package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.Type;
import ic.ast.decl.ClassType;

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

	@Override
	public Type getExpresstionType() {
		return (new ClassType(this.getLine(), this.getName()));
	}

}
