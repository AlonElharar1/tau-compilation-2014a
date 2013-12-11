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

public class IceCoffeInterpreter implements Visitor {

	private Program program;
	
	public IceCoffeInterpreter(Program program) {
	}

	public Object executeMethod(String method, Object[] parameters) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.Program)
	 */
	@Override
	public Object visit(Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclClass)
	 */
	@Override
	public Object visit(DeclClass icClass) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclField)
	 */
	@Override
	public Object visit(DeclField field) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclVirtualMethod)
	 */
	@Override
	public Object visit(DeclVirtualMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclStaticMethod)
	 */
	@Override
	public Object visit(DeclStaticMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclLibraryMethod)
	 */
	@Override
	public Object visit(DeclLibraryMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.Parameter)
	 */
	@Override
	public Object visit(Parameter formal) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.PrimitiveType)
	 */
	@Override
	public Object visit(PrimitiveType type) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.ClassType)
	 */
	@Override
	public Object visit(ClassType type) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtAssignment)
	 */
	@Override
	public Object visit(StmtAssignment assignment) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtCall)
	 */
	@Override
	public Object visit(StmtCall callStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtReturn)
	 */
	@Override
	public Object visit(StmtReturn returnStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtIf)
	 */
	@Override
	public Object visit(StmtIf ifStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtWhile)
	 */
	@Override
	public Object visit(StmtWhile whileStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtBreak)
	 */
	@Override
	public Object visit(StmtBreak breakStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtContinue)
	 */
	@Override
	public Object visit(StmtContinue continueStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtBlock)
	 */
	@Override
	public Object visit(StmtBlock statementsBlock) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.LocalVariable)
	 */
	@Override
	public Object visit(LocalVariable localVariable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefVariable)
	 */
	@Override
	public Object visit(RefVariable location) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefField)
	 */
	@Override
	public Object visit(RefField location) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefArrayElement)
	 */
	@Override
	public Object visit(RefArrayElement location) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.StaticCall)
	 */
	@Override
	public Object visit(StaticCall call) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.VirtualCall)
	 */
	@Override
	public Object visit(VirtualCall call) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.This)
	 */
	@Override
	public Object visit(This thisExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.NewInstance)
	 */
	@Override
	public Object visit(NewInstance newClass) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.NewArray)
	 */
	@Override
	public Object visit(NewArray newArray) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.Length)
	 */
	@Override
	public Object visit(Length length) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.Literal)
	 */
	@Override
	public Object visit(Literal literal) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.UnaryOp)
	 */
	@Override
	public Object visit(UnaryOp unaryOp) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.BinaryOp)
	 */
	@Override
	public Object visit(BinaryOp binaryOp) {
		// TODO Auto-generated method stub
		return null;
	}

}
