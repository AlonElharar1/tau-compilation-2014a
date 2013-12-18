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
import ic.ast.expr.Ref;
import ic.ast.expr.RefVariable;

import java.util.HashMap;

public class MethodScope extends StatementBlockScope {

	private DeclMethod scopeMethod;
	
	private HashMap<String, Parameter> parameters = new HashMap<String, Parameter>();

	/**
	 * @param parentScope
	 * @param statments
	 */
	public MethodScope(IceCoffeScope parentScope, DeclMethod method) {
		super(parentScope, method.getStatements());
		
		this.scopeMethod = method;
		
		for (Parameter param : this.scopeMethod.getFormals()) {
			this.parameters.put(param.getName(), param);
		}
	}

	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#currentMethod()
	 */
	@Override
	public DeclMethod currentMethod() {
		return (this.scopeMethod);
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#findRef(ic.ast.expr.Ref)
	 */
	@Override
	public Node findRef(Ref location) {
		if (!(location instanceof RefVariable) ||
			(!this.parameters.containsKey(((RefVariable)location).getName())))
			return (super.findRef(location));
				
		return (this.parameters.get(((RefVariable)location).getName()));
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#getScopeName()
	 */
	@Override
	public String getScopeName() {
		return (this.scopeMethod.getName());
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.StatementBlockScope#getScopeType()
	 */
	@Override
	public String getScopeType() {
		return ("Method");
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.StatementBlockScope#internalPrint()
	 */
	@Override
	protected void internalPrint() {
		
		for (String paramId : this.parameters.keySet()) {
			System.out.printf("\tParameter:\t%s : %s", 
					this.parameters.get(paramId).getName(),
					this.parameters.get(paramId).getType());
		}
		
		super.internalPrint();
	}
}
