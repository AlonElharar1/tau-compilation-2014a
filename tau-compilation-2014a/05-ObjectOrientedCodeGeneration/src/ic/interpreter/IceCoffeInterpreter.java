/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.interpreter;

import ic.ast.Visitor;
import ic.ast.decl.ClassType;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclLibraryMethod;
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

public abstract class IceCoffeInterpreter implements Visitor {

	protected Program program;
	
	public IceCoffeInterpreter(Program program) {
		this.program = program;
	}
	
	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.Program)
	 */
	@Override
	public Object visit(Program program) {
		throw new InterpreterRunTimeException(program.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclClass)
	 */
	@Override
	public Object visit(DeclClass icClass) {
		throw new InterpreterRunTimeException(icClass.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclField)
	 */
	@Override
	public Object visit(DeclField field) {
		throw new InterpreterRunTimeException(field.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclVirtualMethod)
	 */
	@Override
	public Object visit(DeclVirtualMethod method) {
		throw new InterpreterRunTimeException(method.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclStaticMethod)
	 */
	@Override
	public Object visit(DeclStaticMethod method) {
		throw new InterpreterRunTimeException(method.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclLibraryMethod)
	 */
	@Override
	public Object visit(DeclLibraryMethod method) {
		throw new InterpreterRunTimeException(method.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.Parameter)
	 */
	@Override
	public Object visit(Parameter formal) {
		throw new InterpreterRunTimeException(formal.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.PrimitiveType)
	 */
	@Override
	public Object visit(PrimitiveType type) {
		throw new InterpreterRunTimeException(type.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.ClassType)
	 */
	@Override
	public Object visit(ClassType type) {
		throw new InterpreterRunTimeException(type.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtAssignment)
	 */
	@Override
	public Object visit(StmtAssignment assignment) {
		throw new InterpreterRunTimeException(assignment.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtCall)
	 */
	@Override
	public Object visit(StmtCall callStatement) {
		throw new InterpreterRunTimeException(callStatement.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtReturn)
	 */
	@Override
	public Object visit(StmtReturn returnStatement) {
		throw new InterpreterRunTimeException(returnStatement.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtIf)
	 */
	@Override
	public Object visit(StmtIf ifStatement) {
		throw new InterpreterRunTimeException(ifStatement.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtWhile)
	 */
	@Override
	public Object visit(StmtWhile whileStatement) {
		throw new InterpreterRunTimeException(whileStatement.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtBreak)
	 */
	@Override
	public Object visit(StmtBreak breakStatement) {
		throw new InterpreterRunTimeException(breakStatement.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtContinue)
	 */
	@Override
	public Object visit(StmtContinue continueStatement) {
		throw new InterpreterRunTimeException(continueStatement.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtBlock)
	 */
	@Override
	public Object visit(StmtBlock statementsBlock) {
		throw new InterpreterRunTimeException(statementsBlock.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.LocalVariable)
	 */
	@Override
	public Object visit(LocalVariable localVariable) {
		throw new InterpreterRunTimeException(localVariable.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefVariable)
	 */
	@Override
	public Object visit(RefVariable location) {
		throw new InterpreterRunTimeException(location.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefField)
	 */
	@Override
	public Object visit(RefField location) {
		throw new InterpreterRunTimeException(location.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefArrayElement)
	 */
	@Override
	public Object visit(RefArrayElement location) {
		throw new InterpreterRunTimeException(location.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.StaticCall)
	 */
	@Override
	public Object visit(StaticCall call) {
		throw new InterpreterRunTimeException(call.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.VirtualCall)
	 */
	@Override
	public Object visit(VirtualCall call) {
		throw new InterpreterRunTimeException(call.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.This)
	 */
	@Override
	public Object visit(This thisExpression) {
		throw new InterpreterRunTimeException(thisExpression.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.NewInstance)
	 */
	@Override
	public Object visit(NewInstance newClass) {
		throw new InterpreterRunTimeException(newClass.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.NewArray)
	 */
	@Override
	public Object visit(NewArray newArray) {
		throw new InterpreterRunTimeException(newArray.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.Length)
	 */
	@Override
	public Object visit(Length length) {
		throw new InterpreterRunTimeException(length.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.Literal)
	 */
	@Override
	public Object visit(Literal literal) {
		throw new InterpreterRunTimeException(literal.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.UnaryOp)
	 */
	@Override
	public Object visit(UnaryOp unaryOp) {
		throw new InterpreterRunTimeException(unaryOp.getLine(), 
				"action is not supported by this interpreter");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.BinaryOp)
	 */
	@Override
	public Object visit(BinaryOp binaryOp) {
		throw new InterpreterRunTimeException(binaryOp.getLine(), 
				"action is not supported by this interpreter");
	}

}
