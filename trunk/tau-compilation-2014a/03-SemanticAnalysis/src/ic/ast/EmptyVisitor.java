/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.ast;

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

public abstract class EmptyVisitor implements Visitor {

	@Override
	public Object visit(Program program) {
		return null;
	}

	@Override
	public Object visit(DeclClass icClass) {
		return null;
	}

	@Override
	public Object visit(DeclField field) {
		return null;
	}

	@Override
	public Object visit(DeclVirtualMethod method) {
		return null;
	}

	@Override
	public Object visit(DeclStaticMethod method) {
		return null;
	}

	@Override
	public Object visit(DeclLibraryMethod method) {
		return null;
	}

	@Override
	public Object visit(Parameter formal) {
		return null;
	}

	@Override
	public Object visit(PrimitiveType type) {
		return null;
	}

	@Override
	public Object visit(ClassType type) {
		return null;
	}

	@Override
	public Object visit(StmtAssignment assignment) {
		return null;
	}

	@Override
	public Object visit(StmtCall callStatement) {
		return null;
	}

	@Override
	public Object visit(StmtReturn returnStatement) {
		return null;
	}

	@Override
	public Object visit(StmtIf ifStatement) {
		return null;
	}

	@Override
	public Object visit(StmtWhile whileStatement) {
		return null;
	}

	@Override
	public Object visit(StmtBreak breakStatement) {
		return null;
	}

	@Override
	public Object visit(StmtContinue continueStatement) {
		return null;
	}

	@Override
	public Object visit(StmtBlock statementsBlock) {
		return null;
	}

	@Override
	public Object visit(LocalVariable localVariable) {
		return null;
	}

	@Override
	public Object visit(RefVariable location) {
		return null;
	}

	@Override
	public Object visit(RefField location) {
		return null;
	}

	@Override
	public Object visit(RefArrayElement location) {
		return null;
	}

	@Override
	public Object visit(StaticCall call) {
		return null;
	}

	@Override
	public Object visit(VirtualCall call) {
		return null;
	}

	@Override
	public Object visit(This thisExpression) {
		return null;
	}

	@Override
	public Object visit(NewInstance newClass) {
		return null;
	}

	@Override
	public Object visit(NewArray newArray) {
		return null;
	}

	@Override
	public Object visit(Length length) {
		return null;
	}

	@Override
	public Object visit(Literal literal) {
		return null;
	}

	@Override
	public Object visit(UnaryOp unaryOp) {
		return null;
	}

	@Override
	public Object visit(BinaryOp binaryOp) {
		return null;
	}
}
