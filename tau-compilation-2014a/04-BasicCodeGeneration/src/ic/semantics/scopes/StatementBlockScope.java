/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.Node;
import ic.ast.stmt.LocalVariable;
import ic.ast.stmt.Statement;
import ic.ast.stmt.StmtBlock;
import ic.semantics.SemanticException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class StatementBlockScope extends IceCoffeScope {

	protected HashMap<String, LocalVariable> localVariables = new LinkedHashMap<String, LocalVariable>();
	
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
	}
	
	public boolean isSymbolExists(String id) {
		return (this.localVariables.containsKey(id));
	}
	
	public void addLocalVariable(LocalVariable var) {
		if (this.isSymbolExists(var.getName()))
			throw new SemanticException(var.getLine(), 
					String.format("Id %s already defined in current scope",
							var.getName()));
		
		this.localVariables.put(var.getName(), var);
	}
	
	@Override
	public Node findLocalVariable(String varName) {
		if (this.localVariables.containsKey(varName))
			return (this.localVariables.get(varName));
		
		return (super.findLocalVariable(varName));
	}
	
	@Override
	public String getScopeName() {
		return ("@" + this.currentMethod().getName());
	}
	
	@Override
	public String getScopeType() {
		return ("Statement Block");
	}

	@Override
	protected void internalPrint() {
		for (String localId : this.localVariables.keySet()) {
			System.out.printf("\tLocal variable:\t%s : %s\n", 
					this.localVariables.get(localId).getName(),
					this.localVariables.get(localId).getType());
		}
	}
}
