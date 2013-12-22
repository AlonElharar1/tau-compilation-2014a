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
		
		for (DeclClass classNode : program.getClasses()) {
			if (this.classes.containsKey(classNode.getName()))
				throw new SemanticException(classNode.getLine(), 
						"a class with the same name already exists");
			
			this.classes.put(classNode.getName(), classNode);
		}
	}

	@Override
	public DeclClass findClass(String className) {
		if (!this.classes.containsKey(className))
			return (super.findClass(className));
		
		return (this.classes.get(className));
	}
	
	@Override
	public DeclMethod findMethod(String methodId) {
		String className = methodId.substring(methodId.indexOf('.'));
		
		if (this.classes.containsKey(className))
			return (this.classes.get(className).getScope().findMethod(methodId));
		
		return (super.findMethod(methodId));
	}
	
	@Override
	public DeclField findField(String fieldId) {
		String className = fieldId.substring(fieldId.indexOf('.'));
		
		if (this.classes.containsKey(className))
			return (this.classes.get(className).getScope().findField(fieldId));
		
		return (super.findField(fieldId));
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
}
