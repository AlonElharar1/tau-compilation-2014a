package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.Type;

/**
 * Array length expression AST node.
 * 
 */
public class Length extends Expression {

	private Expression array;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new array length expression node.
	 * 
	 * @param array
	 *            Expression representing an array.
	 */
	public Length(int line, Expression array) {
		super(line);
		this.array = array;
	}

	public Expression getArray() {
		return array;
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
