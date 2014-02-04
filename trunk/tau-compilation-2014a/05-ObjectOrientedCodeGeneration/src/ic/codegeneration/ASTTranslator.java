/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration;

import ic.ast.Node;
import ic.ast.RunThroughVisitor;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclLibraryMethod;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.DeclVirtualMethod;
import ic.ast.decl.Parameter;
import ic.ast.decl.Program;
import ic.ast.expr.BinaryOp;
import ic.ast.expr.BinaryOp.BinaryOps;
import ic.ast.expr.Expression;
import ic.ast.expr.Length;
import ic.ast.expr.Literal;
import ic.ast.expr.NewArray;
import ic.ast.expr.NewInstance;
import ic.ast.expr.RefArrayElement;
import ic.ast.expr.RefField;
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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ASTTranslator extends RunThroughVisitor {

	private static final Label MAIN_LABEL = new Label("_ic_main");

	private _3ACILGenerator generator;
	private RunTimeChecksGenerator checksGenerator;
	
	private HashMap<DeclMethod, Label> libraryLabels = new HashMap<DeclMethod, Label>();
	
	private HashMap<Node, Register> variablesRegisters = new HashMap<Node, Register>();
	
	private Label currWhileStartLabel = null;
	private Label currWhileEndLabel = null;
	
	private final Register THIS_REG = new Register(0);
	
	private VirtualAnalyzer virtualAnalyzer = new VirtualAnalyzer();
	private HashMap<DeclClass, Label> dispatchVectorLabels = new HashMap<DeclClass, Label>();
	
	public ASTTranslator(_3ACILGenerator generator) {
		this.generator = generator;
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
		
		this.virtualAnalyzer.visit(program);
		
		// Add all dispatch vectors into the data
		for (DeclClass icClass : program.getClasses()) {
			if (!icClass.getName().equals("Library")) {
				this.dispatchVectorLabels.put(icClass, 
						this.generator.addDispachVector(icClass.getName(), 
								this.virtualAnalyzer.getDispachVector(icClass)));
			}
		}
		
		program.accept(this);
	}
	
	public void write(PrintStream stream) {
		this.generator.write(stream);
	}
	
	@Override
	public Object visit(Program program) {

		// Goto the main function
		this.generator.addOpcode(OpCodes.GOTO, MAIN_LABEL);
		
		return (super.visit(program));
	}

	@Override
	public Object visit(DeclMethod method) {
		
		// Add the method label
		Label methodLabel = method.getName().equals("main") ?
				MAIN_LABEL : new Label(method.getId());
		this.generator.addLabel(methodLabel);
		
		this.generator.startNewRegisterContext();
		
		// Assign parameters with registers
		for (Parameter param : method.getFormals()) {
			this.variablesRegisters.put(param, this.generator.getFreeRegister());
		}
		
		// Add the statements implementations
		super.visit(method);
		
		// Just to be safe, end the method with an 'RET' instruction
		this.generator.addOpcode(OpCodes.RET);
		
		this.generator.endRegisterContext();
		
		return (null);
	}
	
	@Override
	public Object visit(DeclLibraryMethod method) {
		// Ignore library methods implementation
		return (null);
	}
	
	@Override
	public Object visit(LocalVariable localVariable) {

		// Assign the variable with an register
		Register varReg = this.generator.getFreeRegister();
		this.variablesRegisters.put(localVariable, varReg);
		
		// Assign the initial value if exists
		if (localVariable.getInitialValue() != null) {
			Operand initValReg = this.generator.addGetInstruction(
					localVariable.getInitialValue().accept(this));
			
			this.generator.addOpcode(OpCodes.MOV, initValReg, varReg);
		}

		return (varReg);
	}
	
	@Override
	public Object visit(StmtAssignment assignment) {
		
		this.generator.addSetInstruction(
				assignment.getAssignment().accept(this),
				assignment.getVariable().accept(this));
		
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

		Label ifEndLabel = this.generator.generateUniqueLabel("endif");
		
		Operand condReg = this.generator.addGetInstruction(
				ifStatement.getCondition().accept(this));
		
		if (ifStatement.getElseOperation() != null) {
			Label elseLabel = this.generator.generateUniqueLabel("else");
			
			this.generator.addOpcode(OpCodes.NIF, condReg, elseLabel);
			
			ifStatement.getOperation().accept(this);
			this.generator.addOpcode(OpCodes.GOTO, ifEndLabel);
			
			this.generator.addLabel(elseLabel);
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
			
			Operand retVal = this.generator.addGetInstruction(
					returnStatement.getValue().accept(this));
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
		this.currWhileStartLabel = this.generator.generateUniqueLabel("while_start");
		this.currWhileEndLabel = this.generator.generateUniqueLabel("while_end");
		
		// Generate the while opcodes
		this.generator.addLabel(this.currWhileStartLabel);
		
		Operand condReg = this.generator.addGetInstruction(
				whileStatement.getCondition().accept(this));
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
		
		Register resultReg = this.generator.getFreeRegister();
		
		// Get the operands values
		Operand firstReg = this.generator.addGetInstruction(
				binaryOp.getFirstOperand().accept(this));
		
		// Short-Circuit
		Label shortCircuitLabel = null;
		
		if ((binaryOp.getOperator() == BinaryOps.LAND) ||
		 	(binaryOp.getOperator() == BinaryOps.LOR)) {
			shortCircuitLabel = this.generator.generateUniqueLabel("shortcircit");
			this.generator.addOpcode(OpCodes.MOV, firstReg, resultReg);
			this.generator.addOpcode((binaryOp.getOperator() == BinaryOps.LAND) ?
					OpCodes.NIF : OpCodes.IF, resultReg, shortCircuitLabel);
		}
		
		Operand secondReg = this.generator.addGetInstruction(
				binaryOp.getSecondOperand().accept(this));
		
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
			this.checksGenerator.emitDivisionByZeroCheck(secondReg);
			this.generator.addOpcode(OpCodes.DIV, firstReg, secondReg, resultReg);
			break;
		case MOD:
			this.checksGenerator.emitDivisionByZeroCheck(secondReg);
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
			this.generator.addOpcode(OpCodes.AND, resultReg, secondReg, resultReg);
			break;
		case LOR:
			this.generator.addOpcode(OpCodes.OR, resultReg, secondReg, resultReg);
			break;
		default:
			break;
		}
		
		if (shortCircuitLabel != null)
			this.generator.addLabel(shortCircuitLabel);
		
		return (resultReg);
	}
	
	@Override
	public Object visit(Length length) {
		
		// Get the array pointer
		Register arrayLocation = (Register)length.getArray().accept(this);
		
		// Emit null check
		this.checksGenerator.emitNullCheck(arrayLocation);
		
		// The array length is in the first 32bits
		Register lengthReg = this.generator.getFreeRegister();
		this.generator.addOpcode(OpCodes.READ, arrayLocation, lengthReg);
		
		return (lengthReg);
	}
	
	@Override
	public Object visit(Literal literal) {

		// Set a new free register with the literal value
		Operand data = null;
		
		switch (literal.getDataType()) {
		case BOOLEAN:
			data = new Immediate((Boolean)literal.getValue() ? 1 : 0);
			
			break;
		case INT:
			data = new Immediate((Integer)literal.getValue());
			
			break;
		case VOID:
			data = new Immediate(0);
			
			break;
		case STRING:
			data = this.generator.addString(literal.getValue().toString());
			
			break;
		default:
			break;
		}
		
		return (data);
	}
	
	@Override
	public Object visit(NewArray newArray) {
		
		// Get a register for the array pointer
		Register arrayPtrReg = this.generator.getFreeRegister();

		// Get the array size
		Operand sizeReg = this.generator.addGetInstruction(
				newArray.getSize().accept(this));
		
		// Emit size check
		this.checksGenerator.emitArraySizeCheck(sizeReg);
		
		// Add space for the length
		Register actualSize = this.generator.getFreeRegister();
		this.generator.addOpcode(OpCodes.ADD, sizeReg, new Immediate(1), actualSize);
		
		// Allocate a new array
		this.generator.addOpcode(OpCodes.PARAM, actualSize);
		this.generator.addOpcode(OpCodes.CALLINTO, new Label("alloc"), arrayPtrReg);
		
		// Put the array size in the first cell
		this.generator.addOpcode(OpCodes.WRITE, arrayPtrReg, sizeReg);
		
		return (arrayPtrReg);
	}
	
	@Override
	public Object visit(NewInstance newClass) {
		
		// Get a register for the instance pointer
		Register instancePtrReg = this.generator.getFreeRegister();
		
		// Get the instance allocation size
		DeclClass instnaceClass = newClass.getScope().findClass(newClass.getName());
		Operand sizeOpernad = new Immediate(this.virtualAnalyzer.sizeOf(instnaceClass));
		
		// Allocate the instance in memory
		this.generator.addOpcode(OpCodes.PARAM, sizeOpernad);
		this.generator.addOpcode(OpCodes.CALLINTO, new Label("alloc"), instancePtrReg);
		
		// Put the dispatch vector in the first cell
		this.generator.addOpcode(OpCodes.WRITE, instancePtrReg, 
				this.dispatchVectorLabels.get(instnaceClass));
		
		return (instancePtrReg);
	}
	
	@Override
	public Object visit(RefArrayElement location) {
		
		// Get the array pointer and index into registers
		Register arrayLocation = (Register)location.getArray().accept(this);
		
		// Emit check for the array pointer
		this.checksGenerator.emitNullCheck(arrayLocation);

		// Emit check for the index
		Operand indexReg = this.generator.addGetInstruction(
				location.getIndex().accept(this));
		this.checksGenerator.emitArrayIndexCheck(arrayLocation, indexReg);

		// Calculate the cell location
		Register cellPtrReg = this.generator.getFreeRegister();
		this.generator.addOpcode(OpCodes.ADD, arrayLocation, indexReg, cellPtrReg);
		this.generator.addOpcode(OpCodes.ADD, cellPtrReg, new Immediate(1), cellPtrReg);
		
		return (new MemoryLocation(cellPtrReg));
	}
	
	@Override
	public Object visit(RefVariable location) {
		
		Node varNode = location.getScope().findLocalVariable(location.getName());
		
		if (varNode instanceof DeclField) {
			RefField fieldLocation = 
					new RefField(location.getLine(), null, location.getName());
			fieldLocation.setScope(location.getScope());
			return (this.visit(fieldLocation));
		}
		
		Register varReg = this.variablesRegisters.get(varNode);
		
		return (varReg);
	}
	
	@Override
	public Object visit(RefField location) {

		// Get the object location
		Operand obj = (location.getObject() == null) ? 
				THIS_REG : (Operand)location.getObject().accept(this);
		
		// Add the field offset
		Register fieldReg = this.generator.getFreeRegister();
		DeclField field = location.getScope().findField(location.getField());
		this.generator.addOpcode(OpCodes.ADD, obj, 
				new Immediate(this.virtualAnalyzer.getOffset(field)), fieldReg);
				
		return (new MemoryLocation(fieldReg));
	}
	
	@Override
	public Object visit(StaticCall call) {

		DeclStaticMethod method = 
				call.getScope().findStaticMethod(call.getClassName(), call.getMethod());
		
		// Get the arguments
		List<Operand> arguments = 
				new ArrayList<Operand>(call.getArguments().size());
		
		for (Expression expr : call.getArguments()) {
			arguments.add(this.generator.addGetInstruction(expr.accept(this)));
		}
		
		// Set the arguments as parameters
		for (Operand register : arguments) {
			this.generator.addOpcode(OpCodes.PARAM, register);
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
			retVal = this.generator.getFreeRegister();
			this.generator.addOpcode(OpCodes.CALLINTO, methodLabel, retVal);
		}
		
		return (retVal);
	}
	
	@Override
	public Object visit(UnaryOp unaryOp) {
		
		Register resultReg = this.generator.getFreeRegister();
		
		Operand operandReg = this.generator.addGetInstruction(
				unaryOp.getOperand().accept(this));
		
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
			
			StaticCall staticCall = new StaticCall(
					call.getLine(), 
					call.getScope().currentClass().getName(),
					call.getMethod(),
					call.getArguments());
			staticCall.setScope(call.getScope());
					
			return (staticCall.accept(this));
		}
		// Virtual method
		else {
		
			// Get the object location
			Operand obj = (call.getObject() == null) ? 
					THIS_REG : (Operand)call.getObject().accept(this);
			
			// Get the dispatch vector location
			Register methodLocation = this.generator.getFreeRegister();
			this.generator.addOpcode(OpCodes.READ, obj, methodLocation);
			
			// Add the method offset and read it's address
			String className = (call.getObject() == null) ?
					call.getScope().currentClass().getName() : 
					new TypeAnalyzer().getExpressionType(call.getObject()).getDisplayName();
			DeclVirtualMethod method = call.getScope().findVirtualMethod(
					className, call.getMethod());
					
			this.generator.addOpcode(OpCodes.ADD, methodLocation, 
					new Immediate(this.virtualAnalyzer.getOffset(method)), methodLocation);
			this.generator.addOpcode(OpCodes.READ, methodLocation, methodLocation);
			
			// Get the arguments
			List<Operand> arguments = 
					new ArrayList<Operand>(call.getArguments().size());
			
			for (Expression expr : call.getArguments()) {
				arguments.add(this.generator.addGetInstruction(expr.accept(this)));
			}

			// Set the this parameter
			this.generator.addOpcode(OpCodes.PARAM, obj);
			
			// Set the arguments as parameters
			for (Operand register : arguments) {
				this.generator.addOpcode(OpCodes.PARAM, register);
			}
			
			// Call the method
			Register retVal = null;
			
			if (method.getType().getDisplayName().equals("void")) {
				this.generator.addOpcode(OpCodes.CALL, methodLocation);
			}
			else {
				retVal = this.generator.getFreeRegister();
				this.generator.addOpcode(OpCodes.CALLINTO, methodLocation, retVal);
			}
		
			return (retVal);
		}
	}
}
