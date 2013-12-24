/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.semantics.checks;

import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.Program;
import ic.ast.expr.BinaryOp;
import ic.ast.expr.Expression;
import ic.ast.expr.Length;
import ic.ast.expr.NewArray;
import ic.ast.expr.RefArrayElement;
import ic.ast.expr.RefField;
import ic.ast.expr.StaticCall;
import ic.ast.expr.This;
import ic.ast.expr.UnaryOp;
import ic.ast.expr.VirtualCall;
import ic.ast.stmt.LocalVariable;
import ic.ast.stmt.Statement;
import ic.ast.stmt.StmtAssignment;
import ic.ast.stmt.StmtBlock;
import ic.ast.stmt.StmtCall;
import ic.ast.stmt.StmtIf;
import ic.ast.stmt.StmtReturn;
import ic.ast.stmt.StmtWhile;
import ic.semantics.SemanticException;

public class ThisCheck extends SemanticCheck {

	@Override
	public Object visit(Program program) {
		
		for (DeclClass icClass : program.getClasses())
			icClass.accept(this);
		
		return null;
	}

	@Override
	public Object visit(DeclClass icClass) {
		
		for (DeclMethod method : icClass.getMethods())
			method.accept(this);
		
		return null;
	}

	@Override
	public Object visit(DeclStaticMethod method) {
		
		for (Statement statement : method.getStatements())
			statement.accept(this);
		
		return null;
	}

	@Override
	public Object visit(StmtAssignment assignment) {
		
		assignment.getVariable().accept(this);
		assignment.getAssignment().accept(this);
		
		return null;
	}

	@Override
	public Object visit(StmtCall callStatement) {
		
		callStatement.getCall().accept(this);
		
		return null;
	}

	@Override
	public Object visit(StmtReturn returnStatement) {
		
		returnStatement.getValue().accept(this);
		
		return null;
	}

	@Override
	public Object visit(StmtIf ifStatement) {

		ifStatement.getCondition().accept(this);
		ifStatement.getOperation().accept(this);
		
		if (ifStatement.getElseOperation() != null)
			ifStatement.getElseOperation().accept(this);
		
		return null;
	}

	@Override
	public Object visit(StmtWhile whileStatement) {
		
		whileStatement.getCondition().accept(this);
		whileStatement.getOperation().accept(this);
		
		return null;
	}

	@Override
	public Object visit(StmtBlock statementsBlock) {
		
		for (Statement statement : statementsBlock.getStatements())
			statement.accept(this);
		
		return null;
	}

	@Override
	public Object visit(LocalVariable localVariable) {

		if (localVariable.getInitialValue() != null)
			localVariable.getInitialValue().accept(this);
		
		return null;
	}

	@Override
	public Object visit(RefField location) {
		
		location.getObject().accept(this);
		
		return null;
	}

	@Override
	public Object visit(RefArrayElement location) {
		
		location.getArray().accept(this);
		location.getIndex().accept(this);
		
		return null;
	}

	@Override
	public Object visit(StaticCall call) {

		for (Expression expression : call.getArguments())
			expression.accept(this);
		
		return null;
	}

	@Override
	public Object visit(VirtualCall call) {
		
		if (call.getObject() != null)
			call.getObject().accept(this);
		
		for (Expression expression : call.getArguments())
			expression.accept(this);
		
		return null;
	}

	@Override
	public Object visit(This thisExpression) {
		throw new SemanticException(thisExpression.getLine(),
				"Use of 'this' expression inside static method is not allowed");
	}
	
	@Override
	public Object visit(NewArray newArray) {
		
		newArray.getSize().accept(this);
		
		return null;
	}

	@Override
	public Object visit(Length length) {
		
		length.getArray().accept(this);
		
		return null;
	}

	@Override
	public Object visit(UnaryOp unaryOp) {

		unaryOp.getOperand().accept(this);
		
		return null;
	}

	@Override
	public Object visit(BinaryOp binaryOp) {
		
		binaryOp.getFirstOperand().accept(this);
		binaryOp.getSecondOperand().accept(this);
		
		return null;
	}
	
	@Override
	public void runCheck(Program program) {
		program.accept(this);	
	}
}
