package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.PrimitiveType;
import ic.ast.decl.Type;

/**
 * Literal value AST node.
 * 
 */
public class Literal extends Expression {

	private PrimitiveType type;

	private Object value;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new literal node, with a value.
	 * 
	 * @param line
	 *            Line number of the literal.
	 * @param type
	 *            Literal type.
	 * @param value
	 *            Value of literal.
	 */
	public Literal(int line, PrimitiveType.DataType type, Object value) {
		super(line);
		this.type = new PrimitiveType(line, type);
		this.value = value;
	}

	public PrimitiveType.DataType getDataType() {
		return (type.getDataType());
	}
	
	public Type getType() {
		return (type);
	}

	public Object getValue() {
		return value;
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
