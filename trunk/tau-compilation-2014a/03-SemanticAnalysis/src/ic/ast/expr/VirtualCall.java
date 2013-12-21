package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.ClassType;
import ic.ast.decl.Type;
import ic.semantics.SemanticException;

import java.util.List;

/**
 * Virtual method call AST node.
 * 
 */
public class VirtualCall extends Call {

	private Expression object = null;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new virtual method call node.
	 * 
	 * @param line
	 *            Line number of call statement.
	 * @param methodName
	 *            Name of method.
	 * @param arguments
	 *            List of all method arguments.
	 */
	public VirtualCall(int line, String methodName, List<Expression> arguments) {
		super(line, methodName, arguments);
	}

	/**
	 * Constructs a new virtual method call node, for an object other than "this".
	 * 
	 * @param line
	 *            Line number of call statement.
	 * @param object
	 *            object of method call.
	 * @param methodName
	 *            Name of method.
	 * @param arguments
	 *            List of all method arguments.
	 */
	public VirtualCall(int line, Expression object, String methodName,
			List<Expression> arguments) {
		this(line, methodName, arguments);
		this.object = object;
	}

	public boolean hasExplicitObject() {
		return (object != null);
	}

	public Expression getObject() {
		return object;
	}

	@Override
	public Type getExpresstionType() {
		
		Type objectType = this.getObject().getExpresstionType();
		
		if (objectType instanceof ClassType) {
			return (this.getScope().findMethod(
					((ClassType) objectType).getClassName(),
					this.getMethod()).getType());
		}
		
		throw new SemanticException(this.getLine(), 
				"method doesn't exists");
	}

}
