/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import java.util.ArrayList;
import java.util.List;

import ic.ast.Node;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclMethod;
import ic.ast.expr.Ref;

public abstract class IceCoffeScope {

	protected IceCoffeScope parentScope = null;
	protected List<IceCoffeScope> childrenScopes = new ArrayList<IceCoffeScope>();
	
	public IceCoffeScope(IceCoffeScope parentScope) {
		this.parentScope = parentScope;
		this.parentScope.childrenScopes.add(this);
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

	/**
	 * Gets this scope name
	 * @return
	 */
	public abstract String getScopeName();
	
	public abstract String getScopeType();
	
	protected abstract void internalPrint();
	
	/**
	 * Prints this scope data
	 */
	public void print() {
		
		String title = String.format("%s Symbol Table", this.getScopeType());
		
		if (this.getScopeName() != null)
			title += String.format(": %s", this.getScopeName());
		
		if (this.parentScope != null)
			title += String.format(" (parent = %s)", this.parentScope.getScopeName());
		
		System.out.println(title);
		
		this.internalPrint();
		
		for (IceCoffeScope scope : this.childrenScopes) {
			scope.print();
		}
	}
	
}
