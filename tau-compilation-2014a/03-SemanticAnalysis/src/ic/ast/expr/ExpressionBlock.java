package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.Type;

/**
 * AST node for expression in parentheses.
 * 
 */
public class ExpressionBlock extends Expression {

	private Expression expression;

	public Object accept(Visitor visitor) {
		return (this.expression.accept(visitor));
	}

	/**
	 * Constructs a new expression in parentheses node.
	 * 
	 * @param expression
	 *            The expression.
	 */
	public ExpressionBlock(Expression expression) {
		super(expression.getLine());
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
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
