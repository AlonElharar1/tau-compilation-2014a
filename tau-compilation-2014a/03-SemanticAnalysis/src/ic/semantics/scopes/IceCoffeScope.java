/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.Node;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclMethod;
import ic.ast.expr.Ref;

public abstract class IceCoffeScope {

	protected IceCoffeScope parentScope = null;
	
	public IceCoffeScope(IceCoffeScope parentScope) {
		this.parentScope = parentScope;
	}

	/**
	 * Returns the current class scope
	 * @return
	 */
	public DeclClass currentClass() {
		if (this.parentScope == null)
			return (null);
		
		return (this.parentScope.currentClass());
	}
	
	public DeclMethod currentMethod() {
		if (this.parentScope == null)
			return (null);
		
		return (this.parentScope.currentMethod());
	}
	
	/**
	 * Search a class in this scope or in his parents
	 * @param className
	 * @return
	 */
	public DeclClass findClass(String className) {
		if (this.parentScope == null)
			return (null);
		
		return (this.parentScope.findClass(className));
	}
	
	/**
	 * Search a method in this scope or in his parents
	 * @param methodName
	 * @return
	 */
	public DeclMethod findMethod(String methodId) {
		if (this.parentScope == null)
			return (null);
		
		return (this.parentScope.findMethod(methodId));
	}
	
	/**
	 * Search a Ref deceleration node in this scope or in his parents
	 * @param location
	 * @return
	 */
	public Node findRef(Ref location) {
		if (this.parentScope == null)
			return (null);
		
		return (this.parentScope.findRef(location));
	}

}
