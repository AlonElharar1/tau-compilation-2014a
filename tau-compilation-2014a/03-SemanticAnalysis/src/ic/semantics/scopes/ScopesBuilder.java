/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.semantics.*;
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

import java.util.Stack;

public class ScopesBuilder implements Visitor {

	Stack<IceCoffeScope> scopesStack = new Stack<IceCoffeScope>();
	
	@Override
	public Object visit(Program program) {
		
		program.setScope(new GlobalScope(program));
		this.scopesStack.push(program.getScope());
		
		for (DeclClass classNode : program.getClasses()) {
			classNode.accept(this);
		}
		
		return (this.scopesStack.pop());
	}

	@Override
	public Object visit(DeclClass icClass) {
		
		IceCoffeScope parentScope = this.scopesStack.peek();
		
		if (icClass.hasSuperClass()) {
			
			DeclClass superClass = parentScope.findClass(icClass.getSuperClassName());
			
			if (superClass == null)
				throw new SemanticException(icClass.getLine(),
						String.format("Class %s cannot extend %s, since it's not yet defined",
								icClass.getName(), icClass.getSuperClassName()));
			
			parentScope = parentScope.findClass(icClass.getSuperClassName()).getScope();
		}
		
		((GlobalScope)this.scopesStack.peek()).addClass(icClass);
		
		icClass.setScope(new ClassScope(parentScope, icClass));
		this.scopesStack.push(icClass.getScope());
		
		for (DeclField field : icClass.getFields()) {
			field.accept(this);
		}
		
		for (DeclMethod	method : icClass.getMethods()) {
			method.accept(this);
		}

		return (this.scopesStack.pop());
	}

	@Override
	public Object visit(DeclField field) {
		((ClassScope)this.scopesStack.peek()).addField(field);
		
		field.setScope(this.scopesStack.peek());
		
		return (field.getScope());
	}

	private Object visitMethod(DeclMethod method) {

		method.setScope(new MethodScope(this.scopesStack.peek(), method));
		this.scopesStack.push(method.getScope());
		
		for (Parameter param : method.getFormals()) {
			param.accept(this);
		}
		
		for (Statement stmt : method.getStatements()) {
			stmt.accept(this);
		}
		
		return (this.scopesStack.pop());
	}
	
	@Override
	public Object visit(DeclVirtualMethod method) {
		((ClassScope)this.scopesStack.peek()).addVirtualMethod(method);
		return (this.visitMethod(method));
	}

	@Override
	public Object visit(DeclStaticMethod method) {
		((ClassScope)this.scopesStack.peek()).addStaticMethod(method);
		return (this.visitMethod(method));
	}

	@Override
	public Object visit(DeclLibraryMethod method) {
		((ClassScope)this.scopesStack.peek()).addStaticMethod(method);
		return (this.visitMethod(method));
	}

	@Override
	public Object visit(Parameter formal) {
		((MethodScope)this.scopesStack.peek()).addParameter(formal);
		formal.setScope(this.scopesStack.peek());
		return (formal.getScope());
	}

	@Override
	public Object visit(PrimitiveType type) {
		type.setScope(this.scopesStack.peek());
		return (type.getScope());
	}

	@Override
	public Object visit(ClassType type) {
		type.setScope(this.scopesStack.peek());
		return (type.getScope());
	}

	@Override
	public Object visit(StmtAssignment assignment) {
		assignment.setScope(this.scopesStack.peek());
		
		assignment.getVariable().accept(this);
		assignment.getAssignment().accept(this);
		
		return (assignment.getScope());
	}

	@Override
	public Object visit(StmtCall callStatement) {
		callStatement.setScope(this.scopesStack.peek());
		
		callStatement.getCall().accept(this);
		
		return (callStatement.getScope());
	}

	@Override
	public Object visit(StmtReturn returnStatement) {
		returnStatement.setScope(this.scopesStack.peek());
		
		if (returnStatement.getValue() != null)
			returnStatement.getValue().accept(this);
		
		return (returnStatement.getScope());
	}

	@Override
	public Object visit(StmtIf ifStatement) {
		ifStatement.setScope(this.scopesStack.peek());
		
		ifStatement.getCondition().accept(this);
		ifStatement.getOperation().accept(this);
		
		if (ifStatement.getElseOperation() != null)
			ifStatement.getElseOperation().accept(this);
		
		return (ifStatement.getScope());
	}

	@Override
	public Object visit(StmtWhile whileStatement) {
		whileStatement.setScope(this.scopesStack.peek());
		
		whileStatement.getCondition().accept(this);
		whileStatement.getOperation().accept(this);
		
		return (whileStatement.getScope());
	}

	@Override
	public Object visit(StmtBreak breakStatement) {
		breakStatement.setScope(this.scopesStack.peek());
		return (breakStatement.getScope());
	}

	@Override
	public Object visit(StmtContinue continueStatement) {
		continueStatement.setScope(this.scopesStack.peek());
		return (continueStatement.getScope());
	}

	@Override
	public Object visit(StmtBlock statementsBlock) {
		
		statementsBlock.setScope(
				new StatementBlockScope(this.scopesStack.peek(), statementsBlock));
		this.scopesStack.push(statementsBlock.getScope());
		
		for (Statement stmt : statementsBlock.getStatements()) {
			stmt.accept(this);
		}
		
		return (this.scopesStack.pop());
	}

	@Override
	public Object visit(LocalVariable localVariable) {
		((StatementBlockScope)this.scopesStack.peek()).addLocalVariable(localVariable);
		
		localVariable.setScope(this.scopesStack.peek());
		
		if (localVariable.getInitialValue() != null)
			localVariable.getInitialValue().accept(this);
		
		return (localVariable.getScope());
	}

	@Override
	public Object visit(RefVariable location) {
		location.setScope(this.scopesStack.peek());
		
		return (location.getScope());
	}

	@Override
	public Object visit(RefField location) {
		location.setScope(this.scopesStack.peek());
		
		location.getObject().accept(this);
		
		return (location.getScope());
	}

	@Override
	public Object visit(RefArrayElement location) {
		location.setScope(this.scopesStack.peek());
		
		location.getArray().accept(this);
		location.getIndex().accept(this);
		
		return (location.getScope());
	}

	@Override
	public Object visit(StaticCall call) {
		call.setScope(this.scopesStack.peek());
		
		for (Expression expr : call.getArguments()) {
			expr.accept(this);
		}
		
		return (call.getScope());
	}

	@Override
	public Object visit(VirtualCall call) {
		call.setScope(this.scopesStack.peek());
		
		if (call.getObject() != null)
			call.getObject().accept(this);
		
		for (Expression expr : call.getArguments()) {
			expr.accept(this);
		}
		
		return (call.getScope());
	}

	@Override
	public Object visit(This thisExpression) {
		thisExpression.setScope(this.scopesStack.peek());
		return (thisExpression.getScope());
	}

	@Override
	public Object visit(NewInstance newClass) {
		newClass.setScope(this.scopesStack.peek());
		
		return (newClass.getScope());
	}

	@Override
	public Object visit(NewArray newArray) {
		newArray.setScope(this.scopesStack.peek());
		
		newArray.getType().accept(this);
		newArray.getSize().accept(this);
		
		return (newArray.getScope());
	}

	@Override
	public Object visit(Length length) {
		length.setScope(this.scopesStack.peek());
		
		length.getArray().accept(this);
		
		return (length.getScope());
	}

	@Override
	public Object visit(Literal literal) {
		literal.setScope(this.scopesStack.peek());
		return (literal.getScope());
	}

	@Override
	public Object visit(UnaryOp unaryOp) {
		unaryOp.setScope(this.scopesStack.peek());
		
		unaryOp.getOperand().accept(this);
		
		return (unaryOp.getScope());
	}

	@Override
	public Object visit(BinaryOp binaryOp) {
		binaryOp.setScope(this.scopesStack.peek());
		
		binaryOp.getFirstOperand().accept(this);
		binaryOp.getSecondOperand().accept(this);
		
		return (binaryOp.getScope());
	}
}
