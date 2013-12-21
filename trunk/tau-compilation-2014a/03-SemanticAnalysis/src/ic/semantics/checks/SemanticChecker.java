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
import ic.semantics.SemanticException;

import java.util.ArrayList;
import java.util.List;

public class SemanticChecker {

	private List<SemanticCheck> checks = new ArrayList<SemanticCheck>();
	
	public SemanticChecker() {
		this.addCheck(new AlwaysReturnCheck());
		this.addCheck(new BreakContinueCheck());
		this.addCheck(new LibraryCheck());
		this.addCheck(new LocalVariablesInitializationCheck());
		this.addCheck(new MainCheck());
		this.addCheck(new ScopeRulesCheck());
		this.addCheck(new ThisCheck());
		this.addCheck(new TypingRulesCheck());
	}
	
	public void addCheck(SemanticCheck check) {
		
		checks.add(check);
	}
	
	public void runAllChecks(Program program) throws SemanticException {
	
		for (SemanticCheck check : checks){
			check.runCheck(program);
		}
	}
	
}
