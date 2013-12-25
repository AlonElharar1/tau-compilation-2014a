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
import ic.codegeneration._3ACILGenerator.OpCodes;
import ic.semantics.scopes.IceCoffeScope;

public class ASTTranslator extends RunThroughVisitor {

	private static final String MAIN_LABEL = "_ic_main";
	
	private _3ACILGenerator generator;
	private RunTimeChecksGenerator checksGenerator;

	private int freeRegister = 1;
	private HashMap<LocalVariable, Integer> variablesRegisters = new HashMap<LocalVariable, Integer>();
	
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
		this.generator.addLabel(method.getId());
		
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
		Integer varReg = ++this.freeRegister;
		this.variablesRegisters.put(localVariable, varReg);
		
		// Assign the initial value if exists
		if (localVariable.getInitialValue() != null) {
			Integer initValReg = (Integer)localVariable.getInitialValue().accept(this);
			
			this.generator.addOpcode(OpCodes.MOV, "$" + initValReg, "$" + varReg);
		}

		return (varReg);
	}
	
	@Override
	public Object visit(StmtAssignment assignment) {
		
		Integer assignValReg = (Integer)assignment.getAssignment().accept(this);
		
		
		
		return (null);
	}
}
