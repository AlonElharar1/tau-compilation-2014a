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
import ic.semantics.SemanticException;

public class ClassStaticScope extends ClassScope {
	
	/**
	 * @param parentScope
	 * @param classNode
	 */
	public ClassStaticScope(IceCoffeScope parentScope, DeclClass classNode) {
		super(parentScope, classNode);
	}

	protected void extractSymbols() {
		for (DeclMethod method : this.scopeClass.getMethods()) {
			if ((method instanceof DeclStaticMethod) ||
				(method instanceof DeclLibraryMethod)) {
				
				String methodId = 
						String.format("%s.%s", 
								this.scopeClass.getName(), 
								method.getName());
				
				if (this.methods.containsKey(methodId))
					throw new SemanticException(method.getLine(), 
							"a method with the same name already exists");
				
				this.methods.put(methodId, method);
			}
		}
	}
	
}
