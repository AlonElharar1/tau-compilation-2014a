package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.Type;

/**
 * Array reference AST node.
 * 
 */
public class RefArrayElement extends Ref {

	private Expression array;

	private Expression index;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new array reference node.
	 * 
	 * @param array
	 *            Expression representing an array.
	 * @param index
	 *            Expression representing a numeric index.
	 */
	public RefArrayElement(Expression array, Expression index) {
		super(array.getLine());
		this.array = array;
		this.index = index;
	}

	public Expression getArray() {
		return array;
	}

	public Expression getIndex() {
		return index;
	}

	/* (non-Javadoc)
	 * @see ic.ast.expr.Expression#getExpresstionType()
	 */
	@Override
	public Type getExpresstionType() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
