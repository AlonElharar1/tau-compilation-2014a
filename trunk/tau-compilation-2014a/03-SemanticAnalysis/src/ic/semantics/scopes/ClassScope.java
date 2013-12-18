/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.Node;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclLibraryMethod;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.DeclVirtualMethod;
import ic.ast.expr.Ref;
import ic.ast.expr.RefField;

import java.util.HashMap;

public class ClassScope extends IceCoffeScope {

	private DeclClass scopeClass;
	
	public HashMap<String, DeclMethod> methods = new HashMap<String, DeclMethod>();
	public HashMap<String, DeclField> fields = new HashMap<String, DeclField>();
	
	/**
	 * @param parentScope
	 */
	public ClassScope(IceCoffeScope parentScope, DeclClass classNode) {
		super(parentScope);
		
		this.scopeClass = classNode;
		
		for (DeclMethod method : this.scopeClass.getMethods()) {
			this.methods.put(
					String.format("%s.%s", this.scopeClass.getName(), method.getName()),
					method);
		}
		
		for (DeclField field : this.scopeClass.getFields()) {
			this.fields.put(
					String.format("%s.%s", this.scopeClass.getName(), field.getName()), 
					field);
		}
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#currentClass()
	 */
	@Override
	public DeclClass currentClass() {
		return (this.scopeClass);
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#findMethod(java.lang.String)
	 */
	@Override
	public DeclMethod findMethod(String methodId) {
		if (!this.methods.containsKey(methodId))
			return (super.findMethod(methodId));
		
		return (this.methods.get(methodId));
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#findRef(ic.ast.expr.Ref)
	 */
	@Override
	public Node findRef(Ref location) {
		if (!(location instanceof RefField) ||
			(!this.fields.containsKey(((RefField)location).getField())))
			return (super.findRef(location));
		
		return (this.fields.get(((RefField)location).getField()));
	}	
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#getScopeName()
	 */
	@Override
	public String getScopeName() {
		return (this.scopeClass.getName());
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#getScopeType()
	 */
	@Override
	public String getScopeType() {
		return ("Class");
	}

	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#internalPrint()
	 */
	@Override
	protected void internalPrint() {
		
		for (String fieldId : this.fields.keySet()) {
			System.out.printf("\tField:\t%s : %s\n",
					this.fields.get(fieldId).getName(),
					this.fields.get(fieldId).getType());
		}
		
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
