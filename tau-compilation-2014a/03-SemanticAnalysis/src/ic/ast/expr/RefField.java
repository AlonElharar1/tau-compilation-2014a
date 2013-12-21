package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.Type;

/**
 * Variable reference AST node.
 * 
 */
public class RefField extends Ref {

	private Expression object;

	private String fieldName;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new variable reference node, for an external location.
	 * 
	 * @param line
	 *            Line number of reference.
	 * @param object
	 *            The object containing the field.
	 * @param name
	 *            Name of variable.
	 */
	public RefField(int line, Expression object, String fieldName) {
		super(line);
		this.fieldName = fieldName;
		this.object = object;
	}

	public Expression getObject() {
		return object;
	}

	public String getField() {
		return fieldName;
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
