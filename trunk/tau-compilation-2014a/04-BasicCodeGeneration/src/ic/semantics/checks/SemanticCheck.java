/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.checks;

import ic.ast.EmptyVisitor;
import ic.ast.Visitor;
import ic.ast.decl.Program;

public abstract class SemanticCheck extends EmptyVisitor implements Visitor {

	public abstract void runCheck(Program program);
	
}
