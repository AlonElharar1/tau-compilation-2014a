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
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.DeclVirtualMethod;
import ic.ast.decl.Parameter;
import ic.ast.decl.PrimitiveType;
import ic.ast.decl.Program;
import ic.ast.expr.BinaryOp;
import ic.ast.expr.Expression;
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
import ic.ast.stmt.Statement;
import ic.ast.stmt.StmtAssignment;
import ic.ast.stmt.StmtBlock;
import ic.ast.stmt.StmtBreak;
import ic.ast.stmt.StmtCall;
import ic.ast.stmt.StmtContinue;
import ic.ast.stmt.StmtIf;
import ic.ast.stmt.StmtReturn;
import ic.ast.stmt.StmtWhile;

public abstract class RunThroughVisitor implements Visitor {

	@Override
	public Object visit(Program program) {
		
		for (DeclClass icClass : program.getClasses()) {
			icClass.accept(this);
		}
		
		return (null);
	}

	@Override
	public Object visit(DeclClass icClass) {
		
		for (DeclField field : icClass.getFields()) {
			field.accept(this);
		}
		
		for (DeclMethod method : icClass.getMethods()) {
			method.accept(this);
		}
		
		return (null);
	}

	@Override
	public Object visit(DeclField field) {
		
		field.getType().accept(this);
		
		return (null);
	}

	public Object visit(DeclMethod method) {
		method.getType().accept(this);
		
		for (Parameter param : method.getFormals()) {
			param.accept(this);
		}
		
		for (Statement stmt : method.getStatements()) {
			stmt.accept(this);
		}
		
		return (null);
	}
	
	@Override
	public Object visit(DeclVirtualMethod method) {
		return (this.visit(method));
	}

	@Override
	public Object visit(DeclStaticMethod method) {
		return (this.visit(method));
	}

	@Override
	public Object visit(DeclLibraryMethod method) {
		return (this.visit(method));
	}

	@Override
	public Object visit(Parameter formal) {
		
		formal.getType().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(PrimitiveType type) {
		return (null);
	}

	@Override
	public Object visit(ClassType type) {
		return (null);
	}

	@Override
	public Object visit(StmtAssignment assignment) {
		
		assignment.getVariable().accept(this);
		assignment.getAssignment().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(StmtCall callStatement) {
		
		callStatement.getCall().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(StmtReturn returnStatement) {
		
		returnStatement.getValue().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(StmtIf ifStatement) {

		ifStatement.getCondition().accept(this);
		ifStatement.getOperation().accept(this);
		
		if (ifStatement.getElseOperation() != null)
			ifStatement.getElseOperation().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(StmtWhile whileStatement) {
		
		whileStatement.getCondition().accept(this);
		whileStatement.getOperation().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(StmtBreak breakStatement) {
		return (null);
	}

	@Override
	public Object visit(StmtContinue continueStatement) {
		return (null);
	}

	@Override
	public Object visit(StmtBlock statementsBlock) {
		
		for (Statement stmt : statementsBlock.getStatements()) {
			stmt.accept(this);
		}
		
		return (null);
	}

	@Override
	public Object visit(LocalVariable localVariable) {
		
		if (localVariable.getInitialValue() != null)
			localVariable.getInitialValue().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(RefVariable location) {
		return (null);
	}

	@Override
	public Object visit(RefField location) {
		
		if (location.getObject() != null)
			location.getObject().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(RefArrayElement location) {
		
		location.getArray().accept(this);
		location.getIndex().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(StaticCall call) {
		
		for (Expression expr : call.getArguments()) {
			expr.accept(this);
		}
		
		return (null);
	}

	@Override
	public Object visit(VirtualCall call) {
		
		if (call.getObject() != null)
			call.getObject().accept(this);
		
		for (Expression expr : call.getArguments()) {
			expr.accept(this);
		}
		
		return (null);
	}

	@Override
	public Object visit(This thisExpression) {
		return (null);
	}

	@Override
	public Object visit(NewInstance newClass) {
		return (null);
	}

	@Override
	public Object visit(NewArray newArray) {
		
		newArray.getType().accept(this);
		newArray.getSize().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(Length length) {
		
		length.getArray().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(Literal literal) {
		
		literal.getType().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(UnaryOp unaryOp) {
		
		unaryOp.getOperand().accept(this);
		
		return (null);
	}

	@Override
	public Object visit(BinaryOp binaryOp) {
		
		binaryOp.getFirstOperand().accept(this);
		binaryOp.getSecondOperand().accept(this);
		
		return (null);
	}
}
