/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.semantics.checks;

import ic.ast.Node;
import ic.ast.decl.ClassType;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.DeclVirtualMethod;
import ic.ast.decl.Program;
import ic.ast.expr.BinaryOp;
import ic.ast.expr.Expression;
import ic.ast.expr.Length;
import ic.ast.expr.NewArray;
import ic.ast.expr.NewInstance;
import ic.ast.expr.RefArrayElement;
import ic.ast.expr.RefField;
import ic.ast.expr.RefVariable;
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
public class ScopeRulesCheck extends SemanticCheck {

	private TypeAnalyzer typeAnalyzer = new TypeAnalyzer();
	
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
	public Object visit(DeclVirtualMethod method) {
		
		for (Statement statement : method.getStatements())
			statement.accept(this);
		
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

		localVariable.getInitialValue().accept(this);
		
		return null;
	}

	@Override
	public Object visit(RefVariable location) {
		
		Node localVar = location.getScope().findLocalVariable(location.getName());
		
		if ((localVar == null) ||
			(localVar.getLine() > location.getLine()))
				throw new SemanticException(location.getLine(),
						"'" + location.getName() + "' is not declared before used");
		
		return null;
	}
	
	@Override
	public Object visit(RefField location) {
		
		String className = 
				this.typeAnalyzer.getExpressionType(location.getObject()).getDisplayName();
		DeclField field = location.getScope().findField(className, location.getField());
		
		if (field == null)
			throw new SemanticException(location.getLine(),
					"'" + className + "' does not have field: '" + location.getField() + "'");
		
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
			
		if (call.getScope().findStaticMethod(call.getClassName(), call.getMethod()) == null)
			throw new SemanticException(call.getLine(),
					"'" + call.getClassName() + "' does not have the static method: '" + call.getMethod() + "'");
		
		for (Expression expression : call.getArguments())
			expression.accept(this);
		
		return null;
		
	}

	@Override
	public Object visit(VirtualCall call) {
		
		call.getObject().accept(this);
		
		String className = 
				this.typeAnalyzer.getExpressionType(call.getObject()).getDisplayName();

		DeclVirtualMethod method = 
				call.getScope().findVirtualMethod(className, call.getMethod());
		
		if (method == null)  
			throw new SemanticException(call.getLine(),
					"'" + className + "' does not have the virtual method: '" + call.getMethod() + "'");
		
		for (Expression expression : call.getArguments())
			expression.accept(this);
		
		return null;
	}

	@Override
	public Object visit(NewInstance newClass) {
		
		if (newClass.getScope().findClass(newClass.getName()) == null)
			throw new SemanticException(newClass.getLine(),
				"'" + newClass.getName() + "' class does not exists");
		
		return null;
	}
	
	@Override
	public Object visit(NewArray newArray) {

		if ((newArray.getType() instanceof ClassType) &&
			(newArray.getScope().findClass(newArray.getType().getDisplayName()) == null))
			throw new SemanticException(newArray.getLine(),
					"'" + newArray.getType().getDisplayName() + "' class does not exists");
		
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
