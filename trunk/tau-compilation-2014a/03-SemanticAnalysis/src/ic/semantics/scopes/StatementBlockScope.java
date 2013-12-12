/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.Node;
import ic.ast.expr.Ref;
import ic.ast.stmt.LocalVariable;

import java.util.HashMap;

public class StatementBlockScope extends IceCoffeScope {

	private HashMap<String, LocalVariable> localVariables = new HashMap<String, LocalVariable>();
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#findRef(ic.ast.expr.Ref)
	 */
	@Override
	public Node findRef(Ref location) {
		// TODO Auto-generated method stub
		return super.findRef(location);
	}
	
}
