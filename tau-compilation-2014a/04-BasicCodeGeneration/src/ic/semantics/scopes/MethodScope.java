/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.Node;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.Parameter;
import ic.ast.stmt.LocalVariable;
import ic.semantics.SemanticException;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MethodScope extends StatementBlockScope {

	private DeclMethod scopeMethod;
	
	private HashMap<String, Parameter> parameters = new LinkedHashMap<String, Parameter>();

	/**
	 * @param parentScope
	 * @param statments
	 */
	public MethodScope(IceCoffeScope parentScope, DeclMethod method) {
		super(parentScope, method.getStatements());
		
		this.scopeMethod = method;
	}

	@Override
	public boolean isSymbolExists(String id) {
		return (this.parameters.containsKey(id) ||
				super.isSymbolExists(id)); 
	}
	
	public void addParameter(Parameter param) {
		if (this.isSymbolExists(param.getName()))
			throw new SemanticException(param.getLine(), 
					String.format("Id %s already defined in current scope",
							param.getName()));
		
		this.parameters.put(param.getName(), param);
	}
	
	@Override
	public void addLocalVariable(LocalVariable var) {
		
		if (this.parameters.containsKey(var.getName()))
			throw new SemanticException(var.getLine(), 
					String.format("Local variable %s is shadowing a parameter",
							var.getName()));
		
		super.addLocalVariable(var);
	}
	
	@Override
	public Node findLocalVariable(String varName) {
		if (this.parameters.containsKey(varName))
			return (this.parameters.get(varName));
		
		return super.findLocalVariable(varName);
	}
	
	@Override
	public DeclMethod currentMethod() {
		return (this.scopeMethod);
	}
	
	@Override
	public String getScopeName() {
		return (this.scopeMethod.getName());
	}
	
	@Override
	public String getScopeType() {
		return ("Method");
	}
	
	@Override
	protected void internalPrint() {
		
		for (String paramId : this.parameters.keySet()) {
			System.out.printf("\tParameter:\t%s : %s\n", 
					this.parameters.get(paramId).getName(),
					this.parameters.get(paramId).getType());
		}
		
		super.internalPrint();
	}
}
