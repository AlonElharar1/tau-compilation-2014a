/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.decl.DeclClass;
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
;		}
	}

	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#findClass(java.lang.String)
	 */
	@Override
	public DeclClass findClass(String className) {
		if (!this.classes.containsKey(className))
			return (super.findClass(className));
		
		return (this.classes.get(className));
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#findMethod(java.lang.String)
	 */
	@Override
	public DeclMethod findMethod(String methodId) {

		for (DeclClass classNode : this.classes.values()) {
			DeclMethod method = classNode.getScope().findMethod(methodId);
			
			if (method != null)
				return (method);
		}
		
		return (super.findMethod(methodId));
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#getScopeName()
	 */
	@Override
	public String getScopeName() {
		return (null);
	}

	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#getScopeType()
	 */
	@Override
	public String getScopeType() {
		return ("Global");
	}

	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#internalPrint()
	 */
	@Override
	protected void internalPrint() {
		for (String classStr : this.classes.keySet()) {
			System.out.printf("\tClass:\t%s\n", classStr);
		}
	}
}