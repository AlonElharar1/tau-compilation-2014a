package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.Type;

/**
 * Class instance creation AST node.
 * 
 */
public class NewInstance extends New {

	private String name;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new class instance creation expression node.
	 * 
	 * @param line
	 *            Line number of expression.
	 * @param name
	 *            Name of class.
	 */
	public NewInstance(int line, String name) {
		super(line);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see ic.ast.expr.Expression#getType()
	 */
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
