/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.DeclVirtualMethod;
import ic.semantics.SemanticException;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ClassScope extends IceCoffeScope {

	protected DeclClass scopeClass;
	
	public HashMap<String, DeclField> fields = new LinkedHashMap<String, DeclField>();;
	public HashMap<String, DeclStaticMethod> staticMethods = new LinkedHashMap<String, DeclStaticMethod>();
	public HashMap<String, DeclVirtualMethod> virtualMethods = new LinkedHashMap<String, DeclVirtualMethod>();
	
	/**
	 * @param parentScope
	 */
	public ClassScope(IceCoffeScope parentScope, DeclClass classNode) {
		super(parentScope);
		
		this.scopeClass = classNode;
		
		for (DeclField field : this.scopeClass.getFields()) {
			this.fields.put(
					String.format("%s.%s", this.scopeClass.getName(), field.getName()), 
					field);
		}
		
		for (DeclMethod method : this.scopeClass.getMethods()) {
			
			String methodId = 
					String.format("%s.%s", 
							this.scopeClass.getName(), 
							method.getName());
			
			if (this.staticMethods.containsKey(methodId) || 
				this.virtualMethods.containsKey(methodId))
				throw new SemanticException(method.getLine(), 
						"a method with the same name already exists");
			
			if (this.fields.containsKey(method.getName()))
				throw new SemanticException(method.getLine(), 
						"a field with the same name already exists");
			
			
			if (method instanceof DeclVirtualMethod) {
				this.virtualMethods.put(methodId, (DeclVirtualMethod)method);
			}
			else if (method instanceof DeclStaticMethod) {
				this.staticMethods.put(methodId, (DeclStaticMethod)method);
			}
		}
	}
	
	@Override
	public DeclClass currentClass() {
		return (this.scopeClass);
	}
	
	@Override
	public DeclMethod findMethod(String methodId) {
		
		if (this.staticMethods.containsKey(methodId))
			return (this.staticMethods.get(methodId));
		
		if (this.virtualMethods.containsKey(methodId))
			return (this.virtualMethods.get(methodId));
		
		return (super.findMethod(methodId));
	}
	
	@Override
	public DeclField findField(String fieldId) {
		if (this.fields.containsKey(fieldId))
			return (this.fields.get(fieldId));
		
		return (super.findField(fieldId));
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
		
		for (String fieldId : this.fields.keySet()) {
			System.out.printf("\tField:\t%s : %s\n",
					this.fields.get(fieldId).getName(),
					this.fields.get(fieldId).getType());
		}
		
		for (String methodId : this.staticMethods.keySet()) {
			System.out.printf("\tStatic method: %s\n", 
					this.staticMethods.get(methodId));
		}
		
		for (String methodId : this.virtualMethods.keySet()) {
			System.out.printf("\tVirtual method: %s\n", 
					this.virtualMethods.get(methodId));
		}
	}
	
}
