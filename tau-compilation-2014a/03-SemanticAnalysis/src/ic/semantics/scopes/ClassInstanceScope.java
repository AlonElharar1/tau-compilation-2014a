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
import ic.ast.decl.DeclVirtualMethod;
import ic.ast.expr.Ref;
import ic.ast.expr.RefField;
import ic.semantics.SemanticException;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ClassInstanceScope extends ClassScope {

	public HashMap<String, DeclField> fields;
	
	/**
	 * @param parentScope
	 * @param classNode
	 */
	public ClassInstanceScope(IceCoffeScope parentScope, DeclClass classNode) {
		super(parentScope, classNode);
	}
	
	@Override
	protected void extractSymbols() {
		
		this.fields = new LinkedHashMap<String, DeclField>();
		
		for (DeclField field : this.scopeClass.getFields()) {
			this.fields.put(
					String.format("%s.%s", this.scopeClass.getName(), field.getName()), 
					field);
		}
		
		for (DeclMethod method : this.scopeClass.getMethods()) {
			if (method instanceof DeclVirtualMethod) {
				
				String methodId = 
						String.format("%s.%s", 
								this.scopeClass.getName(), 
								method.getName());
				
				if (this.methods.containsKey(methodId))
					throw new SemanticException(method.getLine(), 
							"a method with the same name already exists");
				
				if (this.fields.containsKey(method.getName()))
					throw new SemanticException(method.getLine(), 
							"a field with the same name already exists");
				
				this.methods.put(methodId, method);
			}
		}
	}

	@Override
	public Node findRef(Ref location) {
		if (!(location instanceof RefField) ||
			(!this.fields.containsKey(((RefField)location).getField())))
			return (super.findRef(location));
		
		return (this.fields.get(((RefField)location).getField()));
	}
	
	@Override
	protected void internalPrint() {

		for (String fieldId : this.fields.keySet()) {
			System.out.printf("\tField:\t%s : %s\n",
					this.fields.get(fieldId).getName(),
					this.fields.get(fieldId).getType());
		}
		
		super.internalPrint();
	}
}