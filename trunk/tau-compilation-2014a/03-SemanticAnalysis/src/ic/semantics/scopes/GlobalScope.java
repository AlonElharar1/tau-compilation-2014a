/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import java.util.HashMap;

import ic.ast.decl.DeclClass;

public class GlobalScope extends IceCoffeScope {
	
	private HashMap<String, DeclClass> classes = new HashMap<String, DeclClass>();
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#findClass(java.lang.String)
	 */
	@Override
	public DeclClass findClass(String className) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
