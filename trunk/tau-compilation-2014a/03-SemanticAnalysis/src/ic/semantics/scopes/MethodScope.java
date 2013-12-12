/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.Node;
import ic.ast.decl.Parameter;
import ic.ast.expr.Ref;

import java.util.HashMap;

public class MethodScope extends StatementBlockScope {

	private HashMap<String, Parameter> prameters = new HashMap<String, Parameter>();

	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#findRef(ic.ast.expr.Ref)
	 */
	@Override
	public Node findRef(Ref location) {
		// TODO Auto-generated method stub
		return super.findRef(location);
	}
	
}