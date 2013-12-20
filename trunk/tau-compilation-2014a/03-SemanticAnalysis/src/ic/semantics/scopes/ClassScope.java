/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclLibraryMethod;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.DeclVirtualMethod;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class ClassScope extends IceCoffeScope {

	protected DeclClass scopeClass;
	
	public HashMap<String, DeclMethod> methods = new LinkedHashMap<String, DeclMethod>();
	
	/**
	 * @param parentScope
	 */
	public ClassScope(IceCoffeScope parentScope, DeclClass classNode) {
		super(parentScope);
		
		this.scopeClass = classNode;
		
		this.extractSymbols();

	}
	
	protected abstract void extractSymbols();
	
	@Override
	public DeclClass currentClass() {
		return (this.scopeClass);
	}
	
	@Override
	public DeclMethod findMethod(String methodId) {
		if (!this.methods.containsKey(methodId))
			return (super.findMethod(methodId));
		
		return (this.methods.get(methodId));
	}
	
	@Override
	public String getScopeName() {
		return (this.scopeClass.getName());
	}
	
	@Override
	public String getScopeType() {
		return ("Class");
	}

	@Override
	protected void internalPrint() {
		
		for (String methodId : this.methods.keySet()) {
			
			DeclMethod method = this.methods.get(methodId);
			
			if (method instanceof DeclVirtualMethod) {
				System.out.printf("\tVirtual Mehod: %s\n", method);
			}
			else if (method instanceof DeclStaticMethod) {
				System.out.printf("\tStatic Mehod: %s\n", method);
			}
			else if (method instanceof DeclLibraryMethod) {
				System.out.printf("\tLibrary Mehod: %s\n", method);
			}
		}
	}
}
