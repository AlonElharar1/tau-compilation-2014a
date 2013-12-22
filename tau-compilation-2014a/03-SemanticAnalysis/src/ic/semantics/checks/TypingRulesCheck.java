/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.checks;

import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclLibraryMethod;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.DeclVirtualMethod;
import ic.ast.decl.Parameter;
import ic.ast.decl.PrimitiveType;
import ic.ast.decl.Program;
import ic.ast.decl.Type;
import ic.ast.expr.BinaryOp;
import ic.ast.expr.Expression;
import ic.ast.expr.ExpressionBlock;
import ic.ast.expr.NewArray;
import ic.ast.expr.RefArrayElement;
import ic.ast.expr.RefField;
import ic.ast.expr.StaticCall;
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
import ic.semantics.TypeAnalyzer;

import java.util.List;

public class TypingRulesCheck extends SemanticCheck {

	private TypeAnalyzer typeAnalyzer = new TypeAnalyzer();

	@Override
	public Object visit(Program program) {

		for (DeclClass icClass : program.getClasses())
			icClass.accept(this);

		return null;
	}

	@Override
	public Object visit(DeclClass icClass) {

		for (DeclField field : icClass.getFields())
			field.accept(this);

		for (DeclMethod method : icClass.getMethods())
			method.accept(this);

		return null;
	}

	@Override
	public Object visit(DeclField field) {

		if (!field.getScope().isTypeExist(field.getType()))
			throw new SemanticException(field.getLine(), 
					"type '" + field.getType().getDisplayName() + "' is not defined");

		return null;
	}

	@Override
	public Object visit(DeclVirtualMethod method) {

		if (!method.getScope().isTypeExist(method.getType()))
			throw new SemanticException(method.getLine(), 
					"type '" + method.getType().getDisplayName() + "' is not defined");

		for (Parameter parameter : method.getFormals())
			parameter.accept(this);

		for (Statement statement : method.getStatements())
			statement.accept(this);

		return null;
	}

	@Override
	public Object visit(DeclStaticMethod method) {

		if (!method.getScope().isTypeExist(method.getType()))
			throw new SemanticException(method.getLine(), 
					"type '" + method.getType().getDisplayName() + "' is not defined");

		for (Parameter parameter : method.getFormals())
			parameter.accept(this);

		for (Statement statement : method.getStatements())
			statement.accept(this);

		return null;
	}

	@Override
	public Object visit(DeclLibraryMethod method) {

		if (!method.getScope().isTypeExist(method.getType()))
			throw new SemanticException(method.getLine(), 
					"type '" + method.getType().getDisplayName() + "' is not defined");

		for (Parameter parameter : method.getFormals())
			parameter.accept(this);

		for (Statement statement : method.getStatements())
			statement.accept(this);

		return null;
	}

	@Override
	public Object visit(Parameter formal) {
		
		if (!formal.getScope().isTypeExist(formal.getType()))
			throw new SemanticException(formal.getLine(), 
					"type '" + formal.getType().getDisplayName() + "' is not defined");

		return null;
	}

	@Override
	public Object visit(StmtAssignment assignment) {

		assignment.getVariable().accept(this);
		assignment.getAssignment().accept(this);

		Type varType = 
				this.typeAnalyzer.getExpressionType(assignment.getVariable());
		Type assigmentType = 
				this.typeAnalyzer.getExpressionType(assignment.getAssignment());

		if (!this.typeAnalyzer.isInstanceOf(assignment.getScope(), 
				assigmentType, varType))
			throw new SemanticException(assignment.getLine(),
					String.format("Invalid assignment of type %s to variable of type %s",
							assigmentType.getDisplayName(), varType.getDisplayName()));

		return null;
	}

	@Override
	public Object visit(StmtCall callStatement) {

		callStatement.getCall().accept(this);

		return null;
	}

	@Override
	public Object visit(StmtReturn returnStatement) {

		Type expectedType = 
				returnStatement.getScope().currentMethod().getType();
		
		if (expectedType.getDisplayName().equals("void")) { 
			if (returnStatement.getValue() == null)
				return null;
			
			throw new SemanticException(returnStatement.getLine(),
					"method declared as 'void'");
		}
		else if (returnStatement.getValue() != null) {
			
			returnStatement.getValue().accept(this);
			
			Type returnType =
					this.typeAnalyzer.getExpressionType(returnStatement.getValue());
			
			if (this.typeAnalyzer.isInstanceOf(returnStatement.getScope(),
					returnType, expectedType))
				return null;
			
			throw new SemanticException(returnStatement.getLine(),
					String.format("Return statement is not of type %s", 
							returnType.getDisplayName()));
		}
		
		throw new SemanticException(returnStatement.getLine(),
				String.format("Return statement is not of type void")); 
	}

	@Override
	public Object visit(StmtIf ifStatement) {

		ifStatement.getCondition().accept(this);
		
		Type condType = 
				this.typeAnalyzer.getExpressionType(ifStatement.getCondition());
		
		if (!condType.getDisplayName().equals(PrimitiveType.DataType.BOOLEAN.toString()))
			throw new SemanticException(ifStatement.getLine(),
					"Boolean expression is expected");
		
		ifStatement.getOperation().accept(this);
		
		if (ifStatement.getElseOperation() != null)
			ifStatement.getElseOperation().accept(this);

		return null;
	}

	@Override
	public Object visit(StmtWhile whileStatement) {

		whileStatement.getCondition().accept(this);
		
		Type condType = 
				this.typeAnalyzer.getExpressionType(whileStatement.getCondition());
		
		if (!condType.getDisplayName().equals(PrimitiveType.DataType.BOOLEAN.toString()))
			throw new SemanticException(whileStatement.getLine(),
					"Non boolean condition for while statement");

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

		if (!localVariable.getScope().isTypeExist(localVariable.getType()))
			throw new SemanticException(localVariable.getLine(), 
					String.format("type '%s' is not defined", 
							localVariable.getType().getDisplayName()));
		
		if (localVariable.getInitialValue() != null) {
			
			localVariable.getInitialValue().accept(this);
	
			Type initValType = 
					this.typeAnalyzer.getExpressionType(localVariable.getInitialValue());
			
			if (!this.typeAnalyzer.isInstanceOf(localVariable.getScope(),
					initValType, localVariable.getType())) {
				
				throw new SemanticException(localVariable.getLine(),
						String.format("Invalid assignment of type %s to variable of type %s",
								initValType.getDisplayName(),
								localVariable.getType().getDisplayName()));
			}
		}
		
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
		
		if (this.typeAnalyzer.getExpressionType(location.getArray()).getArrayDimension() < 1)
			throw new SemanticException(location.getLine(),
					"expression is not an array");

		location.getIndex().accept(this);
		
		if (!this.typeAnalyzer.getExpressionType(location.getIndex()).getDisplayName().equals(
				PrimitiveType.DataType.INT.toString()))
			throw new SemanticException(location.getLine(),
					"Index must be intenger");

		return null;
	}

	@Override
	public Object visit(StaticCall call) {
		
		List<Parameter> params = call.getScope().findMethod(
				call.getClassName(), call.getMethod()).getFormals();
		List<Expression> args = call.getArguments();
		
		if (params.size() != args.size())
			throw new SemanticException(call.getLine(),
					String.format("Invalid number of arguments for %s.%s",
							call.getClassName(), call.getMethod()));

		for (Expression expression : call.getArguments())
			expression.accept(this);
		
		for (int i = 0; i < args.size(); i++) {
			if (!this.typeAnalyzer.isInstanceOf(call.getScope(),
					this.typeAnalyzer.getExpressionType(args.get(i)), 
					params.get(i).getType()))
				
				throw new SemanticException(call.getLine(),
						String.format("Method %s.%s is not applicable for the arguments given", 
								call.getClassName(), call.getMethod()));
		}

		return null;
	}

	@Override
	public Object visit(VirtualCall call) {

		if (call.getObject() != null)
			call.getObject().accept(this);
		
		String className = (call.getObject() == null) ?
				call.getScope().currentClass().getName() :
					this.typeAnalyzer.getExpressionType(call.getObject()).getDisplayName();
		
		List<Parameter> params =
				call.getScope().findMethod(className, call.getMethod()).getFormals();
		List<Expression> args = call.getArguments();
		
		if (params.size() != args.size())
			throw new SemanticException(call.getLine(),
					"wrong number of arguments");

		for (Expression expression : call.getArguments())
			expression.accept(this);
		
		for (int i = 0; i < args.size(); i++) {
			if (!this.typeAnalyzer.isInstanceOf(call.getScope(),
					this.typeAnalyzer.getExpressionType(args.get(i)), 
					params.get(i).getType()))
				
				throw new SemanticException(call.getLine(),
						String.format("argument '%s' type mismatch", 
								params.get(i).getName()));
		}

		return null;
	}

	@Override
	public Object visit(NewArray newArray) {

		newArray.getSize().accept(this);
		
		if (!this.typeAnalyzer.getExpressionType(newArray.getSize()).getDisplayName().equals(
				PrimitiveType.DataType.INT.toString()))
			throw new SemanticException(newArray.getLine(),
					"Index must be intenger");

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

	public Object visit(ExpressionBlock expressionBlock) {

		expressionBlock.getExpression().accept(this);

		return null;
	}

	@Override
	public void runCheck(Program program) {
		program.accept(this);
	}

}
