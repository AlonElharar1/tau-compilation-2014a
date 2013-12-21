package ic.ast.expr;

import ic.ast.Node;
import ic.ast.decl.Type;

/**
 * Abstract base class for expression AST nodes.
 * 
 */
public abstract class Expression extends Node {

	/**
	 * Constructs a new expression node. Used by subclasses.
	 * 
	 * @param line
	 *            Line number of expression.
	 */
	protected Expression(int line) {
		super(line);
	}
	
	public abstract Type getExpresstionType();
}
