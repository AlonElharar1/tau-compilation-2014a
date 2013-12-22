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
	}
	
	public boolean isFieldExists(String id) {
		if (!this.fields.containsKey(id)) {
			
			if (this.parentScope instanceof ClassScope) {
				return (((ClassScope)this.parentScope).isFieldExists(id));
			}
			else {
				return (false);
			}
		}
					
		return (true);
	}
	
	public boolean isMethodExists(String id) {
		if (!this.virtualMethods.containsKey(id) &&
			!this.staticMethods.containsKey(id)) {
			
			if (this.parentScope instanceof ClassScope) {
				return (((ClassScope)this.parentScope).isMethodExists(id));
			}
			else {
				return (false);
			}
		}
		return (true);
	}
	
	public boolean isSymbolExists(String id) {
		return (this.isFieldExists(id) ||
				this.isMethodExists(id));
	}
	
	public void addField(DeclField field) {
		
		if (this.isFieldExists(field.getName()))
			throw new SemanticException(field.getLine(), 
					String.format("Field %s is shadowing a field with the same name",
							field.getName()));
		else if (this.isSymbolExists(field.getName()))
			throw new SemanticException(field.getLine(), 
					String.format("Id %s already defined in current scope",
							field.getName()));

		this.fields.put(field.getName(), field);
	}
	
	private <T extends DeclMethod> void addMethod(T method,
			HashMap<String, T> hashMap) {
		
		if (this.isFieldExists(method.getName()))
			throw new SemanticException(method.getLine(), 
					String.format("Method %s is shadowing a field with the same name",
							method.getName()));
		else if (this.isMethodExists(method.getName()) &&
				 ((method instanceof DeclStaticMethod) ||
				  (this.findMethod(method.getName()) instanceof DeclStaticMethod)))
			throw new SemanticException(method.getLine(), 
					String.format("method '%s' overloads a different method with the same name",
							method.getName()));
		
		hashMap.put(method.getName(), method);
	}
	
	public void addVirtualMethod(DeclVirtualMethod method) {
		this.addMethod(method, this.virtualMethods);
	}
	
	public void addStaticMethod(DeclStaticMethod method) {
		this.addMethod(method, this.staticMethods);
	}
	
	@Override
	public DeclClass currentClass() {
		return (this.scopeClass);
	}
	
	@Override
	public DeclField findField(String fieldId) {
		if (this.fields.containsKey(fieldId))
			return (this.fields.get(fieldId));
		
		return (super.findField(fieldId));
	}
	
	@Override
	public Node findLocalVariable(String varName) {
		DeclField field = this.findField(varName);
		
		if (field != null)
			return (field);
		
		return (super.findLocalVariable(varName));
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
			System.out.printf("\tField:  %s : %s\n",
					this.fields.get(fieldId).getName(),
					this.fields.get(fieldId).getType());
		}
		
		for (String methodId : this.virtualMethods.keySet()) {
			System.out.printf("\tVirtual method:  %s\n", 
					this.virtualMethods.get(methodId));
		}
		
		for (String methodId : this.staticMethods.keySet()) {
			System.out.printf("\tStatic method:  %s\n", 
					this.staticMethods.get(methodId));
		}
	}
	
}
