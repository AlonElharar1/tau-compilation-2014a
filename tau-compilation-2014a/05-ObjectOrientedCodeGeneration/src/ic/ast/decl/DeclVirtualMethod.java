package ic.ast.decl;

import ic.ast.Visitor;
import ic.ast.stmt.Statement;

import java.util.List;

/**
 * Virtual method AST node.
 * 
 */
public class DeclVirtualMethod extends DeclMethod {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new virtual method node.
	 * 
	 * @param type
	 *            Data type returned by method.
	 * @param name
	 *            Name of method.
	 * @param formals
	 *            List of method parameters.
	 * @param statements
	 *            List of method's statements.
	 */
	public DeclVirtualMethod(Type type, String name, List<Parameter> formals,
			List<Statement> statements) {
		super(type, name, formals, statements);
	}

	public boolean isOverriding() {
		return (this.getOverridenMethod() != null);
	}
	
	public DeclVirtualMethod getOverridenMethod() {
		DeclClass superClass = this.getScope().currentClass().getSuperClass();
		
		if (superClass == null)
			return (null);
		
		DeclMethod method = superClass.getScope().findMethod(this.getName());
		
		return (((method != null) && (method instanceof DeclVirtualMethod)) ? 
				(DeclVirtualMethod)method : null);
	}
}
