package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.ClassType;
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

	@Override
	public Type getExpresstionType() {
		return (new ClassType(this.getLine(), 
				this.getScope().currentClass().getName()));
	}

}
