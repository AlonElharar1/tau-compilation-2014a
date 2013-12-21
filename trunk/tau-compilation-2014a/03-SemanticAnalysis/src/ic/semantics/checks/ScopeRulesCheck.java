/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.semantics.checks;

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
import ic.ast.decl.Type;
import ic.ast.expr.BinaryOp;
import ic.ast.expr.Expression;
import ic.ast.expr.ExpressionBlock;
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
import ic.semantics.SemanticException;

public class ScopeRulesCheck extends SemanticCheck {

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.Program)
	 */
	@Override
	public Object visit(Program program) {
		
		for (DeclClass icClass : program.getClasses())
			icClass.accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclClass)
	 */
	@Override
	public Object visit(DeclClass icClass) {
		
		for (DeclMethod method : icClass.getMethods())
			method.accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclField)
	 */
	@Override
	public Object visit(DeclField field) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclVirtualMethod)
	 */
	@Override
	public Object visit(DeclVirtualMethod method) {
		
		for (Statement statement : method.getStatements())
			statement.accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclStaticMethod)
	 */
	@Override
	public Object visit(DeclStaticMethod method) {
		
		for (Statement statement : method.getStatements())
			statement.accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclLibraryMethod)
	 */
	@Override
	public Object visit(DeclLibraryMethod method) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.Parameter)
	 */
	@Override
	public Object visit(Parameter formal) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.PrimitiveType)
	 */
	@Override
	public Object visit(PrimitiveType type) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.ClassType)
	 */
	@Override
	public Object visit(ClassType type) {
	
		return null;
	}
	
	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.ClassType)
	 */
	@Override
	public Object visit(Literal literal){
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtAssignment)
	 */
	@Override
	public Object visit(StmtAssignment assignment) {
		
		assignment.getVariable().accept(this);
		assignment.getAssignment().accept(this);
		
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtCall)
	 */
	@Override
	public Object visit(StmtCall callStatement) {
		
		callStatement.getCall().accept(this);

		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtReturn)
	 */
	@Override
	public Object visit(StmtReturn returnStatement) {
		
		returnStatement.getValue().accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtIf)
	 */
	@Override
	public Object visit(StmtIf ifStatement) {

		ifStatement.getCondition().accept(this);
		ifStatement.getOperation().accept(this);
		if (ifStatement.getElseOperation() != null)
			ifStatement.getElseOperation().accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtWhile)
	 */
	@Override
	public Object visit(StmtWhile whileStatement) {
		
		whileStatement.getCondition().accept(this);
		whileStatement.getOperation().accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtBreak)
	 */
	@Override
	public Object visit(StmtBreak breakStatement) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtContinue)
	 */
	@Override
	public Object visit(StmtContinue continueStatement) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtBlock)
	 */
	@Override
	public Object visit(StmtBlock statementsBlock) {
		
		for (Statement statement : statementsBlock.getStatements())
			statement.accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.LocalVariable)
	 */
	@Override
	public Object visit(LocalVariable localVariable) {

		localVariable.getInitialValue().accept(this);
		
		return null;
	}


	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefField)
	 */
	@Override
	public Object visit(RefVariable location) {
		
		if ((location.getScope().findRef(location) == null) ||
		    (location.getScope().findRef(location).getLine() > location.getLine()))
				throw new SemanticException(location.getLine(),"'" + location.getName() + "' is not declared before used");
		
		return null;
	}
	

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefField)
	 */
	@Override
	public Object visit(RefField location) {
		
		
		DeclClass objectClass = (DeclClass)location.getObject().accept(this);
		
		for (DeclField field : objectClass.getFields())
			if (field.getName().equals(location.getField()))
				return field.getScope().findClass(field.getType().getDisplayName());
		
	
		throw new SemanticException(location.getLine(),"'" + objectClass.getName() + "' does not have field: '" + location.getField() + "'");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefArrayElement)
	 */
	@Override
	public Object visit(RefArrayElement location) {
		
		
		location.getIndex().accept(this);
		
		return location.getArray().accept(this);
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.StaticCall)
	 */
	@Override
	public Object visit(StaticCall call) {
			
		 if (call.getScope().findMethod(call.getClassName()+ "." + call.getMethod()) == null)
			 throw new SemanticException(call.getLine(),"'" + call.getClassName() + "' does not have static method: '" + call.getMethod() + "'");
		/*boolean isMethodExist = false;
		
		for (DeclMethod method : call.getScope().findClass(call.getClassName()).getMethods())
			if ((method instanceof DeclStaticMethod) && (method.getName().equals(call.getMethod())))
				isMethodExist = true;
		
		if (!isMethodExist)
			throw new SemanticException(call.getLine(),"'" + call.getClassName() + "' does not have static method: '" + call.getMethod() + "'");*/
		
		for (Expression expression : call.getArguments())
			expression.accept(this);
		
		return null;
		
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.VirtualCall)
	 */
	@Override
	public Object visit(VirtualCall call) {
		
		
		
		DeclMethod method = call.getScope().findMethod(call.getScope().currentClass().getName(), call.getMethod()) ;
		
		if (call.getObject() == null){  
			if	(method == null)
				throw new SemanticException(call.getLine(),"The current class does not have virtual method: '" + call.getMethod() + "'");
			return 	call.getScope().findClass(method.getType().getDisplayName());
		}	
		
		
		DeclClass objectClass = (DeclClass)call.getObject().accept(this);
		method = call.getScope().findMethod(objectClass.getName(), call.getMethod());
		
		
		
		if (method == null)
			throw new SemanticException(call.getLine(),"'" + objectClass.getName() + "' does not have virtual method: '" + call.getMethod() + "'");
		
		return 	call.getScope().findClass(method.getType().getDisplayName());
		
		/*
		 * boolean isMethodExist = false;
		 * for (DeclMethod method : objectClass.getMethods())
			if ((method instanceof DeclVirtualMethod) && (method.getName().equals(call.getMethod())))
					isMethodExist = true;	
				
		if (!isMethodExist)
			throw new SemanticException(call.getLine(),"'" + objectClass.getName() + "' does not have virtual method: '" + call.getMethod() + "'");
		
		for (Expression expression : call.getArguments())
			expression.accept(this);
	
		
		return method.getType();*/
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.This)
	 */
	@Override
	public Object visit(This thisExpression) {
		
		return thisExpression.getScope().currentClass();
		
		
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.NewArray)
	 */
	@Override
	public Object visit(NewInstance newClass) {
		
		return newClass.getScope().findClass(newClass.getName());
		
	}
	

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.NewArray)
	 */
	@Override
	public Object visit(NewArray newArray) {
		
		newArray.getSize().accept(this);
		
		//return newArray.getScope().findClass(newArray.getType().getDisplayName());
		return null;
		
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.Length)
	 */
	@Override
	public Object visit(Length length) {
		
		length.getArray().accept(this);
		
		return null;
	}


	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.UnaryOp)
	 */
	@Override
	public Object visit(UnaryOp unaryOp) {

		unaryOp.getOperand().accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.BinaryOp)
	 */
	@Override
	public Object visit(BinaryOp binaryOp) {
		
		binaryOp.getFirstOperand().accept(this);
		binaryOp.getSecondOperand().accept(this);
		
		return null;
	}
	
	public Object visit(ExpressionBlock expressionBlock){
		
		expressionBlock.getExpression().accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.semantics.checks.SemanticCheck#runCheck(ic.ast.decl.Program)
	 */
	@Override
	public void runCheck(Program program) {
		
		program.accept(this);	
	}

}
