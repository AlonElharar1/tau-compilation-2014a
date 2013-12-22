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
import ic.ast.decl.Program;
import ic.semantics.SemanticException;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class GlobalScope extends IceCoffeScope {
	
	private HashMap<String, DeclClass> classes = new LinkedHashMap<String, DeclClass>();
	
	public GlobalScope(Program program) {
		super(null);
	}

	public void addClass(DeclClass classNode) {
		if (this.isSymbolExists(classNode.getName()))
			throw new SemanticException(classNode.getLine(), 
					String.format("Id %s already defined in current scope",
							classNode.getName()));
		
		this.classes.put(classNode.getName(), classNode);
	}
	
	@Override
	public DeclClass findClass(String className) {
		if (!this.classes.containsKey(className))
			return (super.findClass(className));
		
		return (this.classes.get(className));
	}
	
	@Override
	public DeclMethod findMethod(String className, String methodName) {
		if (this.classes.containsKey(className))
			return (this.classes.get(className).getScope().findMethod(methodName));
		
		return (super.findMethod(className, methodName));
	}
	
	@Override
	public DeclField findField(String className, String fieldName) {
		if (this.classes.containsKey(className))
			return (this.classes.get(className).getScope().findField(fieldName));
		
		return (super.findField(className, fieldName));
	}
	
	@Override
	public String getScopeName() {
		return (null);
	}

	@Override
	public String getScopeType() {
		return ("Global");
	}

	@Override
	protected void internalPrint() {
		for (String classStr : this.classes.keySet()) {
			System.out.printf("\tClass:\t%s\n", classStr);
		}
	}

	@Override
	public boolean isSymbolExists(String id) {
		return (this.classes.containsKey(id));
	}
}
