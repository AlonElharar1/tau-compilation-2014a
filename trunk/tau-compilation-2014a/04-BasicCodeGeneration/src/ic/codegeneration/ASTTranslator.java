/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration;

import ic.ast.RunThroughVisitor;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclLibraryMethod;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.Program;
import ic.ast.expr.BinaryOp;
import ic.ast.expr.Expression;
import ic.ast.expr.Length;
import ic.ast.expr.Literal;
import ic.ast.expr.NewArray;
import ic.ast.expr.RefArrayElement;
import ic.ast.expr.RefVariable;
import ic.ast.expr.StaticCall;
import ic.ast.expr.UnaryOp;
import ic.ast.expr.VirtualCall;
import ic.ast.stmt.LocalVariable;
import ic.ast.stmt.StmtAssignment;
import ic.ast.stmt.StmtBreak;
import ic.ast.stmt.StmtContinue;
import ic.ast.stmt.StmtIf;
import ic.ast.stmt.StmtReturn;
import ic.ast.stmt.StmtWhile;
import ic.codegeneration._3acil.Immediate;
import ic.codegeneration._3acil.Label;
import ic.codegeneration._3acil.MemoryLocation;
import ic.codegeneration._3acil.OpCodes;
import ic.codegeneration._3acil.Operand;
import ic.codegeneration._3acil.Register;
import ic.codegeneration._3acil._3ACILGenerator;
import ic.semantics.TypeAnalyzer;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Stack;

public class ASTTranslator extends RunThroughVisitor {

	private static final Label MAIN_LABEL = new Label("_ic_main");
	
	private _3ACILGenerator generator;
	private RunTimeChecksGenerator checksGenerator;

	private Stack<Integer> freeRegisterStack = new Stack<Integer>();
	private HashMap<LocalVariable, Register> variablesRegisters = new HashMap<LocalVariable, Register>();
	
	private Label currWhileStartLabel = null;
	private Label currWhileEndLabel = null;

	private HashMap<DeclMethod, Label> libraryLabels = new HashMap<DeclMethod, Label>();
	
	public ASTTranslator() {
		this.generator = new _3ACILGenerator();
		this.checksGenerator = new RunTimeChecksGenerator(this.generator);
	}
	
	private void findLibaryLabels(Program program) {
		
		DeclClass libraryClass = program.getScope().findClass("Library");
		
		if (libraryClass == null)
			return;
		
		for (DeclMethod libMethod : libraryClass.getMethods()) {
			if (libMethod instanceof DeclLibraryMethod) {
				this.libraryLabels.put(libMethod, new Label(libMethod.getName()));
			}
		}
	}
	
	public void translate(Program program) {
		
		this.findLibaryLabels(program);
		
		program.accept(this);
	}
	
	public void write(PrintStream stream) throws IOException {
		this.generator.write(stream);
	}
	
	@Override
	public Object visit(Program program) {

		// Jump to the main method
		this.generator.addOpcode(OpCodes.CALL, MAIN_LABEL);
		
		// Exit program
		this.generator.addOpcode(OpCodes.PARAM, new Immediate(0));
		this.generator.addOpcode(OpCodes.CALL, new Label("exit"));
		
		return (super.visit(program));
	}
	
	@Override
	public Object visit(DeclMethod method) {
		
		// Add the method label
		Label methodLabel = method.getName().equals("main") ?
				MAIN_LABEL : new Label(method.getId());
		this.generator.addLabel(methodLabel);
		
		// Add the statements implementations
		this.freeRegisterStack.push(method.getFormals().size());
		super.visit(method);
		this.freeRegisterStack.pop();
		
		// Just to be safe, end the method with an 'RET' instruction
		this.generator.addOpcode(OpCodes.RET);
		
		return (null);
	}
	
	@Override
	public Object visit(DeclLibraryMethod method) {
		// Ignore library methods implementation
		return (null);
	}
	
	public Register getFreeRegister() {
		Register reg = new Register(this.freeRegisterStack.peek());
		this.freeRegisterStack.push(this.freeRegisterStack.pop() + 1);
		return (reg);
	}
	
	@Override
	public Object visit(LocalVariable localVariable) {

		// Assign the variable with an register
		Register varReg = this.getFreeRegister();
		this.variablesRegisters.put(localVariable, varReg);
		
		// Assign the initial value if exists
		if (localVariable.getInitialValue() != null) {
			Register initValReg = (Register)localVariable.getInitialValue().accept(this);
			
			this.generator.addOpcode(OpCodes.MOV, initValReg, varReg);
		}

		return (varReg);
	}
	
	@Override
	public Object visit(StmtAssignment assignment) {
		
		Operand ref = (Operand)assignment.getVariable().accept(this);
		Register register = (Register)assignment.getAssignment().accept(this);
		
		if (ref instanceof Register) {
			this.generator.addOpcode(OpCodes.MOV, register, ref);
		}
		else if (ref instanceof MemoryLocation) {
			this.generator.addOpcode(OpCodes.WRITE, ref, register);
		}
		
		return (null);
	}
	
	@Override
	public Object visit(StmtBreak breakStatement) {
		
		this.generator.addOpcode(OpCodes.GOTO, this.currWhileEndLabel);
		
		return (null);
	}
	
	@Override
	public Object visit(StmtContinue continueStatement) {
		
		this.generator.addOpcode(OpCodes.GOTO, this.currWhileStartLabel);
		
		return (null);
	}
	
	@Override
	public Object visit(StmtIf ifStatement) {

		Label ifEndLabel = new Label(String.format("if_end_%d", 
				ifStatement.getLine()));
		
		Register condReg = (Register)ifStatement.getCondition().accept(this);
		
		if (ifStatement.getElseOperation() != null) {
			Label ifElseLabel = new Label(String.format("if_else_%d", 
					ifStatement.getLine()));
			
			this.generator.addOpcode(OpCodes.NIF, condReg, ifElseLabel);
			
			ifStatement.getOperation().accept(this);
			this.generator.addOpcode(OpCodes.GOTO, ifEndLabel);
			
			this.generator.addLabel(ifElseLabel);
			ifStatement.getElseOperation().accept(this);
		}
		else {
			
			this.generator.addOpcode(OpCodes.NIF, condReg, ifEndLabel);
			ifStatement.getOperation().accept(this);
		}

		this.generator.addLabel(ifEndLabel);
		
		return (null);
	}
	
	@Override
	public Object visit(StmtReturn returnStatement) {
		
		if (returnStatement.getValue() == null) {
			this.generator.addOpcode(OpCodes.RET);
		}
		else {
			
			Register retVal = (Register)returnStatement.getValue().accept(this);
			this.generator.addOpcode(OpCodes.RETVAL, retVal);
		}
		
		return (null);
	}
	
	@Override
	public Object visit(StmtWhile whileStatement) {
		
		// Save the previous while labels
		Label prevStartLabel = this.currWhileStartLabel;
		Label prevEndLabel = this.currWhileEndLabel;
		
		// Create new while labels
		this.currWhileStartLabel = new Label(String.format("while_start_%d", 
				whileStatement.getLine()));
		this.currWhileEndLabel = new Label(String.format("while_end_%d", 
				whileStatement.getLine()));
		
		// Generate the while opcodes
		this.generator.addLabel(this.currWhileStartLabel);
		
		Register condReg = (Register)whileStatement.getCondition().accept(this);
		this.generator.addOpcode(OpCodes.NIF, condReg, this.currWhileEndLabel);
		
		whileStatement.getOperation().accept(this);
		
		this.generator.addOpcode(OpCodes.GOTO, this.currWhileStartLabel);
		
		this.generator.addLabel(this.currWhileEndLabel);
		
		// Restore previous while labels
		this.currWhileEndLabel = prevEndLabel;
		this.currWhileStartLabel = prevStartLabel;
		
		return (null);
	}
	
	@Override
	public Object visit(BinaryOp binaryOp) {
		
		TypeAnalyzer typeAnalyzer = new TypeAnalyzer();
		
		// Get the operands values
		Register firstReg = (Register)binaryOp.getFirstOperand().accept(this);
		Register secondReg = (Register)binaryOp.getSecondOperand().accept(this);

		Register resultReg = this.getFreeRegister();
		
		// Call the binary operator instruction
		switch (binaryOp.getOperator()) {
		case PLUS:
			if (typeAnalyzer.getExpressionType(binaryOp.getFirstOperand())
					.getDisplayName().equals("string")) {
				
				this.generator.addOpcode(OpCodes.PARAM, firstReg);
				this.generator.addOpcode(OpCodes.PARAM, secondReg);
				this.generator.addOpcode(OpCodes.CALLINTO, 
						new Label("stringCat"), resultReg);
			}
			else {
				
				this.generator.addOpcode(OpCodes.ADD, firstReg, secondReg, resultReg);
			}
			
			break;
		case MINUS:
			this.generator.addOpcode(OpCodes.SUB, firstReg, secondReg, resultReg);
			break;
		case MULTIPLY:
			this.generator.addOpcode(OpCodes.MUL, firstReg, secondReg, resultReg);
			break;
		case DIVIDE:
			this.generator.addOpcode(OpCodes.DIV, firstReg, secondReg, resultReg);
			break;
		case MOD:
			this.generator.addOpcode(OpCodes.MOD, firstReg, secondReg, resultReg);
			break;
		case EQUAL:
			this.generator.addOpcode(OpCodes.EQ, firstReg, secondReg, resultReg);
			break;
		case NEQUAL:
			this.generator.addOpcode(OpCodes.NEQ, firstReg, secondReg, resultReg);
			break;
		case GT:
			this.generator.addOpcode(OpCodes.GT, firstReg, secondReg, resultReg);
			break;
		case GTE:
			this.generator.addOpcode(OpCodes.GTE, firstReg, secondReg, resultReg);
			break;
		case LT:
			this.generator.addOpcode(OpCodes.LT, firstReg, secondReg, resultReg);
			break;
		case LTE:
			this.generator.addOpcode(OpCodes.LTE, firstReg, secondReg, resultReg);
			break;
		case LAND:
			this.generator.addOpcode(OpCodes.AND, firstReg, secondReg, resultReg);
			break;
		case LOR:
			this.generator.addOpcode(OpCodes.OR, firstReg, secondReg, resultReg);
			break;
		default:
			break;
		}
		
		return (resultReg);
	}
	
	@Override
	public Object visit(Length length) {
		
		// Get the array pointer
		Register arrayPtrReg = (Register)length.getArray().accept(this);
		
		// The array length is in the first 32bits
		Register lengthReg = this.getFreeRegister();
		this.generator.addOpcode(OpCodes.READ, arrayPtrReg, lengthReg);
		
		return (lengthReg);
	}
	
	@Override
	public Object visit(Literal literal) {

		// Set a new free register with the literal value
		Register literalReg = this.getFreeRegister();
		
		switch (literal.getDataType()) {
		case BOOLEAN:
		case INT:
			this.generator.addOpcode(OpCodes.MOV, 
					new Immediate((Integer)literal.getValue()), literalReg);
			
			break;
		case VOID:
			this.generator.addOpcode(OpCodes.MOV, 
					new Immediate(0), literalReg);
			
			break;
		case STRING:
			Label strLabel = this.generator.generateUniqueLabel();
			this.generator.addData(strLabel, (String)literal.getValue());
			
			this.generator.addOpcode(OpCodes.MOV, 
					strLabel, literalReg);
			
			break;
		default:
			break;
		}
		
		return (literalReg);
	}
	
	@Override
	public Object visit(NewArray newArray) {
		
		// Get a register for the array pointer
		Register arrayReg = this.getFreeRegister();

		// Get the array size
		Register sizeReg = (Register)newArray.getSize().accept(this);
		this.generator.addOpcode(OpCodes.ADD, sizeReg, new Immediate(1), sizeReg);
		
		// Allocate a new array
		this.generator.addOpcode(OpCodes.PARAM, sizeReg);
		this.generator.addOpcode(OpCodes.CALLINTO, new Label("alloc"), arrayReg);
		
		// Put the array size in the first cell
		this.generator.addOpcode(OpCodes.WRITE, arrayReg, sizeReg);
		
		return (arrayReg);
	}
	
	@Override
	public Object visit(RefArrayElement location) {
		
		Register cellPtrReg = this.getFreeRegister();
		
		// Get the array pointer and index into registers
		Register arrayPtrReg = (Register)location.getArray().accept(this);
		Register indexReg = (Register)location.getIndex().accept(this);
		
		// Calculate the cell location
		this.generator.addOpcode(OpCodes.ADD, arrayPtrReg, indexReg, cellPtrReg);
		this.generator.addOpcode(OpCodes.ADD, cellPtrReg, new Immediate(1), cellPtrReg);
		
		return (cellPtrReg);
	}
	
	@Override
	public Object visit(RefVariable location) {
		
		Register varReg =  this.variablesRegisters.get(
				location.getScope().findLocalVariable(location.getName()));
		
		return (varReg);
	}
	
	@Override
	public Object visit(StaticCall call) {

		DeclStaticMethod method = 
				call.getScope().findStaticMethod(call.getClassName(), call.getMethod());
		
		// Set arguments
		for (Expression expr : call.getArguments()) {
			Register argReg = (Register)expr.accept(this);
			this.generator.addOpcode(OpCodes.PARAM, argReg);
		}
		
		// Find the method label
		Label methodLabel = (method instanceof DeclLibraryMethod) ?
				this.libraryLabels.get(method) : new Label(method.getId());
		
		// Call the method
		Register retVal = null;
				
		if (method.getType().getDisplayName().equals("void")) {
			this.generator.addOpcode(OpCodes.CALL, methodLabel);
		}
		else {
			retVal = this.getFreeRegister();
			this.generator.addOpcode(OpCodes.CALL, methodLabel, retVal);
		}
		
		return (retVal);
	}
	
	@Override
	public Object visit(UnaryOp unaryOp) {
		
		Register resultReg = this.getFreeRegister();
		
		Register operandReg = (Register)unaryOp.getOperand().accept(this);
		
		switch (unaryOp.getOperator()) {
		case LNEG:
			
			this.generator.addOpcode(OpCodes.NOT, operandReg, resultReg);
			
			break;
		case UMINUS:
			
			this.generator.addOpcode(OpCodes.NEG, operandReg, resultReg);
			
			break;
		default:
			break;
		}
		
		return (resultReg);
	}
	
	@Override
	public Object visit(VirtualCall call) {
		
		// Check if it's a local static call
		if ((call.getObject() == null) &&
			(call.getScope().findStaticMethod(call.getScope().currentClass().getName(),
					call.getMethod()) != null)) {
			
			return (new StaticCall(call.getLine(), 
					call.getScope().currentClass().getName(),
					call.getMethod(),
					call.getArguments()).accept(this));
		}
		
		return (null);
	}
}
