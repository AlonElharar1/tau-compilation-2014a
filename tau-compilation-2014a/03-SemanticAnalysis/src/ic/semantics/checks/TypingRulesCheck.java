/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.semantics.checks;

import java.util.List;

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
import ic.semantics.scopes.IceCoffeScope;

public class TypingRulesCheck extends SemanticCheck {

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
		
		for (DeclField field : icClass.getFields())
			field.accept(this);
				
		
		for (DeclMethod method : icClass.getMethods())
			method.accept(this);
				
		
		return null;
	}
	
	private void typeExistCheck(Type type, int line){
		
			if (!(type instanceof PrimitiveType) && (type.getScope().findClass(type.getDisplayName()) == null))
				throw new SemanticException(line,"Class '" + type.getDisplayName() + "' is not defined");
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclField)
	 */
	@Override
	public Object visit(DeclField field) {
		
		typeExistCheck(field.getType(),field.getLine());
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclVirtualMethod)
	 */
	@Override
	public Object visit(DeclVirtualMethod method) {

		typeExistCheck(method.getType(),method.getLine());
			
		for (Parameter parameter : method.getFormals())
			parameter.accept(this);
				
		for (Statement statement : method.getStatements())
			statement.accept(this);
				
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclStaticMethod)
	 */
	@Override
	public Object visit(DeclStaticMethod method) {
		
		typeExistCheck(method.getType(),method.getLine());
		
		for (Parameter parameter : method.getFormals())
			parameter.accept(this);
				
		for (Statement statement : method.getStatements())
			statement.accept(this);
				
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclLibraryMethod)
	 */
	@Override
	public Object visit(DeclLibraryMethod method) {
		
		typeExistCheck(method.getType(),method.getLine());
		
		for (Parameter parameter : method.getFormals())
			parameter.accept(this);
				
		for (Statement statement : method.getStatements())
			statement.accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.Parameter)
	 */
	@Override
	public Object visit(Parameter formal) {
		
		typeExistCheck(formal.getType(),formal.getLine());
		
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
		
		Type varType  = (Type) assignment.getVariable().getExpresstionType();
		Type assigmentType = (Type) assignment.getAssignment().getExpresstionType();
		
		if (!isSubType(varType,assigmentType))
			throw new SemanticException(assignment.getLine(),"Can not convert " + assigmentType.getDisplayName() + " to " + varType.getDisplayName());

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
		
		String methodType = returnStatement.getScope().currentMethod().getType().getDisplayName();
		if (returnStatement.getValue() == null){
			if (!methodType.equals(PrimitiveType.DataType.VOID))
				throw new SemanticException(returnStatement.getLine(),"The return value is void but expected " + methodType);
			return null;
		}
		
		String returnType =  returnStatement.getValue().getExpresstionType().getDisplayName();
		
		if (!returnType.equals(methodType))
			throw new SemanticException(returnStatement.getLine(),"The return value is " + returnType + "but expected " + methodType);
		
		returnStatement.getValue().accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtIf)
	 */
	@Override
	public Object visit(StmtIf ifStatement) {

		
		Type condType = ifStatement.getCondition().getExpresstionType();
		if (!condType.getDisplayName().equals(PrimitiveType.DataType.BOOLEAN))
			throw new SemanticException(ifStatement.getLine(),"Boolean expression is expected");
		
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
		
		Type condType = whileStatement.getCondition().getExpresstionType();
		if (!condType.getDisplayName().equals(PrimitiveType.DataType.BOOLEAN))
			throw new SemanticException(whileStatement.getLine(),"Boolean expression is expected");
		
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
		
		typeExistCheck(localVariable.getType(),localVariable.getLine());
		
		Type varType  = (Type) localVariable.getType();
		Type initialValueType = (Type) localVariable.getInitialValue().getExpresstionType();
		
		
		if (!isSubType(varType,initialValueType))
			throw new SemanticException(localVariable.getLine(),"Can not convert " + initialValueType.getDisplayName() + " to " + varType.getDisplayName());
		
		return null;
	}
	
	private boolean isSubType(Type type,Type subType){
		
		IceCoffeScope scope = type.getScope();
		
		DeclClass currClass = scope.findClass(subType.getDisplayName());
		DeclClass varClass = scope.findClass(type.getDisplayName());
		
		while (!currClass.getName().equals(varClass.getName())){
			
			String superClassName = currClass.getSuperClassName();
			currClass = scope.findClass(superClassName);
			if (currClass == null)
				return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefField)
	 */
	@Override
	public Object visit(RefVariable location) {
		
		
		return null;
	}
	

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefField)
	 */
	@Override
	public Object visit(RefField location) {
		
		location.getObject().accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefArrayElement)
	 */
	@Override
	public Object visit(RefArrayElement location) {
		
		if (!location.getIndex().getExpresstionType().equals(PrimitiveType.DataType.INT))
			 throw new SemanticException(location.getLine(),"Index must be int type");
		
		if (location.getArray().getExpresstionType().getArrayDimension() < 1)
			 throw new SemanticException(location.getLine(),"The expression is not an array type");
		
		location.getIndex().accept(this);
		location.getArray().accept(this);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.StaticCall)
	 */
	@Override
	public Object visit(StaticCall call) {
		
		List<Parameter> params = call.getScope().findMethod(call.getClassName(), call.getMethod()).getFormals();
		List<Expression> args = call.getArguments();
		if (params.size() != args.size())
			 throw new SemanticException(call.getLine(),"Wrong number of arguments");
		
		
		for (int i = 0; i < args.size() ; i++){
			if (!isSubType(params.get(i).getType(),args.get(i).getExpresstionType()))
				 throw new SemanticException(call.getLine(),"Argument number " + i + " is " + args.get(i).getExpresstionType() + " and expected: " +  params.get(i).getType());
		}
		
		for (Expression expression : call.getArguments())
			expression.accept(this);
		
		return null;
		
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.VirtualCall)
	 */
	@Override
	public Object visit(VirtualCall call) {
		
		List<Parameter> params = call.getScope().findMethod(call.getObject().getExpresstionType().getDisplayName(), call.getMethod()).getFormals();
		List<Expression> args = call.getArguments();
		if (params.size() != args.size())
			 throw new SemanticException(call.getLine(),"Wrong number of arguments");
		
		
		for (int i = 0; i < args.size() ; i++){
			if (!isSubType(params.get(i).getType(),args.get(i).getExpresstionType()))
				 throw new SemanticException(call.getLine(),"Argument number " + i + " is " + args.get(i).getExpresstionType() + " and expected: " +  params.get(i).getType());
		}
		
		for (Expression expression : call.getArguments())
			expression.accept(this);
		
		call.getObject().accept(this);
		
		return null;
		
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.This)
	 */
	@Override
	public Object visit(This thisExpression) {
		
		return null;
		
		
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.NewArray)
	 */
	@Override
	public Object visit(NewInstance newClass) {
		
		return null;
		
	}
	

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.NewArray)
	 */
	@Override
	public Object visit(NewArray newArray) {
		
		if (!newArray.getSize().getExpresstionType().equals(PrimitiveType.DataType.INT))
			throw new SemanticException(newArray.getLine(),"Index must be int type");
		
		newArray.getSize().accept(this);
		
		return null;
		
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.Length)
	 */
	@Override
	public Object visit(Length length) {
		
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
