/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.Node;
import ic.ast.expr.Ref;
import ic.ast.expr.RefVariable;
import ic.ast.stmt.LocalVariable;
import ic.ast.stmt.Statement;
import ic.ast.stmt.StmtBlock;

import java.util.HashMap;
import java.util.List;

public class StatementBlockScope extends IceCoffeScope {

	protected HashMap<String, LocalVariable> localVariables = new HashMap<String, LocalVariable>();
	
	/**
	 * @param parentScope
	 */
	public StatementBlockScope(IceCoffeScope parentScope, StmtBlock block) {
		this(parentScope, block.getStatements());
	}
	
	/**
	 * @param parentScope
	 */
	public StatementBlockScope(IceCoffeScope parentScope, List<Statement> statments) {
		super(parentScope);
		
		for (Statement stmt : statments) {
			if (stmt instanceof LocalVariable) {
				LocalVariable localVar = (LocalVariable)stmt;
				this.localVariables.put(localVar.getName(), localVar);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#findRef(ic.ast.expr.Ref)
	 */
	@Override
	public Node findRef(Ref location) {
		if (!(location instanceof RefVariable) ||
			(!this.localVariables.containsKey(((RefVariable)location).getName())))
			return (super.findRef(location));
			
		return (this.localVariables.get(((RefVariable)location).getName()));
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#getScopeName()
	 */
	@Override
	public String getScopeName() {
		return ("@" + this.currentMethod().getName());
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#getScopeType()
	 */
	@Override
	public String getScopeType() {
		return ("Statement Block");
	}

	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#internalPrint()
	 */
	@Override
	protected void internalPrint() {
		for (String localId : this.localVariables.keySet()) {
			System.out.printf("\tLocal variable:\t%s : %s\n", 
					this.localVariables.get(localId).getName(),
					this.localVariables.get(localId).getType());
		}
	}
}
