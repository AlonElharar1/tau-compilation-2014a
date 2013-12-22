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
import ic.ast.decl.DeclVirtualMethod;
import ic.ast.decl.Program;
import ic.ast.stmt.Statement;
import ic.ast.stmt.StmtBlock;
import ic.ast.stmt.StmtBreak;
import ic.ast.stmt.StmtContinue;
import ic.ast.stmt.StmtIf;
import ic.ast.stmt.StmtWhile;
import ic.semantics.SemanticException;

public class BreakContinueCheck extends SemanticCheck {
	
	private int whileCount = 0;
	
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
	public Object visit(StmtIf ifStatement) {
		
		ifStatement.getOperation().accept(this);
		
		if (ifStatement.getElseOperation() != null)
			ifStatement.getElseOperation().accept(this);
		
		return null;
	}

	@Override
	public Object visit(StmtWhile whileStatement) {
		
		++this.whileCount;
		
		whileStatement.getOperation().accept(this);
		
		--this.whileCount;
		
		return null;
	}

	@Override
	public Object visit(StmtBreak breakStatement) {
		if (this.whileCount == 0) 
			throw new SemanticException(breakStatement.getLine(),
					"'break' keyword can only be used inside while loop");
		
		return null;
	}

	@Override
	public Object visit(StmtContinue continueStatement) {
		if (this.whileCount == 0) 
			throw new SemanticException(continueStatement.getLine(),
					"'continue' keyword can only be used inside while loop");
		
		return null;
	}

	@Override
	public Object visit(StmtBlock statementsBlock) {
		
		for (Statement statement : statementsBlock.getStatements())
			statement.accept(this);
		
		return null;
	}

	@Override
	public void runCheck(Program program) {
		program.accept(this);
	}
}
