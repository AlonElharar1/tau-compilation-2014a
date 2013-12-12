/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import java.util.HashMap;

import ic.ast.Node;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.Type;
import ic.ast.expr.Ref;

public abstract class IceCoffeScope {

	protected IceCoffeScope parentScope = null;
	
	/**
	 * Search a class in this scope or in his parents
	 * @param className
	 * @return
	 */
	public DeclClass findClass(String className) {
		return null;
	}
	
	/**
	 * Search a method in this scope or in his parents
	 * @param methodName
	 * @return
	 */
	public DeclMethod findMethod(String methodName) {
		return null;
	}
	
	/**
	 * Search a Ref deceleration node in this scope or in his parents
	 * @param location
	 * @return
	 */
	public Node findRef(Ref location) {
		return null;
	}

}
