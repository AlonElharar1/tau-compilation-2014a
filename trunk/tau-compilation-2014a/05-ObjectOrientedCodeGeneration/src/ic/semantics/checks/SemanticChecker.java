/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.checks;

import ic.ast.decl.Program;
import ic.semantics.SemanticException;

import java.util.ArrayList;
import java.util.List;

public class SemanticChecker {

	private List<SemanticCheck> checks = new ArrayList<SemanticCheck>();
	
	public SemanticChecker() {
		
		this.addCheck(new MainCheck());
		this.addCheck(new LibraryCheck());
		this.addCheck(new BreakContinueCheck());
		this.addCheck(new ThisCheck());
		
		this.addCheck(new ScopeRulesCheck());
		this.addCheck(new TypingRulesCheck());
		
		this.addCheck(new LocalVariablesInitializationCheck());
		//this.addCheck(new AlwaysReturnCheck());
	}
	
	public void addCheck(SemanticCheck check) {
		this.checks.add(check);
	}
	
	public void runAllChecks(Program program) throws SemanticException {
		for (SemanticCheck check : checks){
			check.runCheck(program);
		}
	}
	
}
