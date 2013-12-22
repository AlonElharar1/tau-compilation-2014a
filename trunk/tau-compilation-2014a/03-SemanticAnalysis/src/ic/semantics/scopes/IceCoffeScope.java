/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.Node;
import ic.ast.decl.ClassType;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.DeclVirtualMethod;
import ic.ast.decl.PrimitiveType;
import ic.ast.decl.Type;

import java.util.ArrayList;
import java.util.List;


public abstract class IceCoffeScope {

	protected IceCoffeScope parentScope = null;
	protected List<IceCoffeScope> childrenScopes = new ArrayList<IceCoffeScope>();
	
	public IceCoffeScope(IceCoffeScope parentScope) {
		this.parentScope = parentScope;
		if (parentScope != null)
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
	
	public DeclMethod findMethod(String className, String methodName) {
		return (this.findMethod(String.format("%s.%s", className, methodName)));
	}

	public DeclStaticMethod findStaticMethod(String className, String methodName) {
		DeclMethod method = this.findMethod(className, methodName);
		
		if (method instanceof DeclStaticMethod)
			return ((DeclStaticMethod)method);
		
		return (null);
	}
	
	public DeclVirtualMethod findVirtualMethod(String className, String methodName) {
		DeclMethod method = this.findMethod(className, methodName);
		
		if (method instanceof DeclVirtualMethod)
			return ((DeclVirtualMethod)method);
		
		return (null);
	}
	
	public Node findLocalVariable(String varName) {
		if (this.parentScope == null)
			return (null);
		
		return (this.parentScope.findLocalVariable(varName));
	}
	
	public DeclField findField(String className, String fieldName) {
		return (this.findField(String.format("%s.%s", className, fieldName)));
	}
	
	public DeclField findField(String fieldId) {
		if (this.parentScope == null)
			return (null);
		
		return (this.parentScope.findField(fieldId));
	}
	
	public boolean isTypeExist(Type type) {
		return ((type instanceof PrimitiveType) ||
				((type instanceof ClassType) && 
				 (this.findClass(type.getDisplayName()) != null)));
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
		
		if ((this.parentScope != null) && (this.parentScope.getScopeName() != null))
			title += String.format(" (parent = %s)", this.parentScope.getScopeName());
		
		System.out.println(title);
		
		this.internalPrint();

		System.out.println();
		
		for (IceCoffeScope scope : this.childrenScopes) {
			scope.print();
		}
	}
	
}
