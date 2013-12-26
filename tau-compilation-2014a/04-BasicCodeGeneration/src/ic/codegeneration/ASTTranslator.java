/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import ic.ast.RunThroughVisitor;
import ic.ast.Visitor;
import ic.ast.decl.ClassType;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclLibraryMethod;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.DeclVirtualMethod;
import ic.ast.decl.Parameter;
import ic.ast.decl.PrimitiveType;
import ic.ast.decl.Program;
import ic.ast.expr.BinaryOp;
import ic.ast.expr.Length;
import ic.ast.expr.Literal;
import ic.ast.expr.NewArray;
import ic.ast.expr.NewInstance;
import ic.ast.expr.RefArrayElement;
import ic.ast.expr.RefField;
import ic.ast.expr.RefVariable;
import ic.ast.expr.StaticCall;
import ic.ast.expr.This;
import ic.ast.expr.UnaryOp;
import ic.ast.expr.VirtualCall;
import ic.ast.stmt.LocalVariable;
import ic.ast.stmt.StmtAssignment;
import ic.ast.stmt.StmtBlock;
import ic.ast.stmt.StmtBreak;
import ic.ast.stmt.StmtCall;
import ic.ast.stmt.StmtContinue;
import ic.ast.stmt.StmtIf;
import ic.ast.stmt.StmtReturn;
import ic.ast.stmt.StmtWhile;
import ic.codegeneration._3acil.Label;
import ic.codegeneration._3acil.MemoryLocation;
import ic.codegeneration._3acil.Operand;
import ic.codegeneration._3acil.Register;
import ic.codegeneration._3acil._3ACILGenerator;
import ic.codegeneration._3acil.OpCodes;
import ic.semantics.scopes.IceCoffeScope;

public class ASTTranslator extends RunThroughVisitor {

	private static final Label MAIN_LABEL = new Label("_ic_main");
	
	private _3ACILGenerator generator;
	private RunTimeChecksGenerator checksGenerator;

	private int freeRegister = 1;
	private HashMap<LocalVariable, Register> variablesRegisters = new HashMap<LocalVariable, Register>();
	
	private Label currWhileStartLabel = null;
	private Label currWhileEndLabel = null;
	
	public ASTTranslator() {
		this.generator = new _3ACILGenerator();
		this.checksGenerator = new RunTimeChecksGenerator(this.generator);
	}
	
	public void translate(Program program) {
		program.accept(this);
	}
	
	public void write(PrintStream stream) throws IOException {
		this.generator.write(stream);
	}
	
	@Override
	public Object visit(Program program) {

		// Jump to the main method
		this.generator.addOpcode(OpCodes.GOTO, MAIN_LABEL);
		
		return (super.visit(program));
	}
	
	@Override
	public Object visit(DeclMethod method) {
		
		// Add the method label
		Label methodLabel = method.getName().equals("main") ?
				MAIN_LABEL : new Label(method.getId());
		this.generator.addLabel(methodLabel);
		
		// Add the statements implementations
		super.visit(method);
		
		// Just to be safe, end the method with an 'RET' instruction
		this.generator.addOpcode(OpCodes.RET);
		
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
		Register varReg = new Register(++this.freeRegister);
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
}
