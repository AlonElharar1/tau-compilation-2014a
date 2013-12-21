package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.Type;

/**
 * 'This' expression AST node.
 * 
 */
public class This extends Expression {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a 'this' expression node.
	 * 
	 * @param line
	 *            Line number of 'this' expression.
	 */
	public This(int line) {
		super(line);
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
