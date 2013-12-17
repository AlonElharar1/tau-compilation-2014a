/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

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

import java.util.Iterator;
import java.util.Stack;

public class ScopesBuilder implements Visitor {

	Stack<IceCoffeScope> scopesStack = new Stack<IceCoffeScope>();
	
	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.Program)
	 */
	@Override
	public Object visit(Program program) {
		
		program.setScope(new GlobalScope(program));
		this.scopesStack.push(program.getScope());
		
		for (DeclClass classNode : program.getClasses()) {
			classNode.accept(this);
		}
		
		return (this.scopesStack.pop());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclClass)
	 */
	@Override
	public Object visit(DeclClass icClass) {
		
		icClass.setScope(new ClassScope(this.scopesStack.peek(), icClass));
		this.scopesStack.push(icClass.getScope());
		
		for (DeclMethod	method : icClass.getMethods()) {
			method.accept(this);
		}
		
		for (DeclField field : icClass.getFields()) {
			field.accept(this);
		}

		return (this.scopesStack.pop());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclField)
	 */
	@Override
	public Object visit(DeclField field) {
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
	
	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclVirtualMethod)
	 */
	@Override
	public Object visit(DeclVirtualMethod method) {
		return (this.visitMethod(method));
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclStaticMethod)
	 */
	@Override
	public Object visit(DeclStaticMethod method) {
		return (this.visitMethod(method));
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.DeclLibraryMethod)
	 */
	@Override
	public Object visit(DeclLibraryMethod method) {
		return (this.visitMethod(method));
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.Parameter)
	 */
	@Override
	public Object visit(Parameter formal) {
		formal.setScope(this.scopesStack.peek());
		return (formal.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.PrimitiveType)
	 */
	@Override
	public Object visit(PrimitiveType type) {
		type.setScope(this.scopesStack.peek());
		return (type.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.decl.ClassType)
	 */
	@Override
	public Object visit(ClassType type) {
		type.setScope(this.scopesStack.peek());
		return (type.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtAssignment)
	 */
	@Override
	public Object visit(StmtAssignment assignment) {
		assignment.setScope(this.scopesStack.peek());
		return (assignment.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtCall)
	 */
	@Override
	public Object visit(StmtCall callStatement) {
		callStatement.setScope(this.scopesStack.peek());
		return (callStatement.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtReturn)
	 */
	@Override
	public Object visit(StmtReturn returnStatement) {
		returnStatement.setScope(this.scopesStack.peek());
		return (returnStatement.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtIf)
	 */
	@Override
	public Object visit(StmtIf ifStatement) {
		ifStatement.setScope(this.scopesStack.peek());
		return (ifStatement.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtWhile)
	 */
	@Override
	public Object visit(StmtWhile whileStatement) {
		whileStatement.setScope(this.scopesStack.peek());
		return (whileStatement.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtBreak)
	 */
	@Override
	public Object visit(StmtBreak breakStatement) {
		breakStatement.setScope(this.scopesStack.peek());
		return (breakStatement.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtContinue)
	 */
	@Override
	public Object visit(StmtContinue continueStatement) {
		continueStatement.setScope(this.scopesStack.peek());
		return (continueStatement.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.StmtBlock)
	 */
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

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.stmt.LocalVariable)
	 */
	@Override
	public Object visit(LocalVariable localVariable) {
		localVariable.setScope(this.scopesStack.peek());
		return (localVariable.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefVariable)
	 */
	@Override
	public Object visit(RefVariable location) {
		location.setScope(this.scopesStack.peek());
		return (location.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefField)
	 */
	@Override
	public Object visit(RefField location) {
		location.setScope(this.scopesStack.peek());
		return (location.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.RefArrayElement)
	 */
	@Override
	public Object visit(RefArrayElement location) {
		location.setScope(this.scopesStack.peek());
		return (location.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.StaticCall)
	 */
	@Override
	public Object visit(StaticCall call) {
		call.setScope(this.scopesStack.peek());
		return (call.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.VirtualCall)
	 */
	@Override
	public Object visit(VirtualCall call) {
		call.setScope(this.scopesStack.peek());
		return (call.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.This)
	 */
	@Override
	public Object visit(This thisExpression) {
		thisExpression.setScope(this.scopesStack.peek());
		return (thisExpression.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.NewInstance)
	 */
	@Override
	public Object visit(NewInstance newClass) {
		newClass.setScope(this.scopesStack.peek());
		return (newClass.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.NewArray)
	 */
	@Override
	public Object visit(NewArray newArray) {
		newArray.setScope(this.scopesStack.peek());
		return (newArray.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.Length)
	 */
	@Override
	public Object visit(Length length) {
		length.setScope(this.scopesStack.peek());
		return (length.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.Literal)
	 */
	@Override
	public Object visit(Literal literal) {
		literal.setScope(this.scopesStack.peek());
		return (literal.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.UnaryOp)
	 */
	@Override
	public Object visit(UnaryOp unaryOp) {
		unaryOp.setScope(this.scopesStack.peek());
		return (unaryOp.getScope());
	}

	/* (non-Javadoc)
	 * @see ic.ast.Visitor#visit(ic.ast.expr.BinaryOp)
	 */
	@Override
	public Object visit(BinaryOp binaryOp) {
		binaryOp.setScope(this.scopesStack.peek());
		return (binaryOp.getScope());
	}
}
