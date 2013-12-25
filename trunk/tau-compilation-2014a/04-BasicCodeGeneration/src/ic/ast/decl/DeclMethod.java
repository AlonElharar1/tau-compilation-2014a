package ic.ast.decl;

import ic.ast.Node;
import ic.ast.stmt.Statement;

import java.util.List;

/**
 * Abstract base class for method AST nodes.
 * 
 */
public abstract class DeclMethod extends Node {

	protected Type type;

	protected String name;

	protected List<Parameter> formals;

	protected List<Statement> statements;

	/**
	 * Constructs a new method node. Used by subclasses.
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
	protected DeclMethod(Type type, String name, List<Parameter> formals,
			List<Statement> statements) {
		super(type.getLine());
		this.type = type;
		this.name = name;
		this.formals = formals;
		this.statements = statements;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public List<Parameter> getFormals() {
		return formals;
	}

	public List<Statement> getStatements() {
		return statements;
	}
	
	public boolean hasSameSignature(DeclMethod method) {
		
		if (!this.getType().getDisplayName().equals(method.getType().getDisplayName()))
			return (false);

		if (this.getFormals().size() != method.getFormals().size())
			return (false);
		
		for (int i = 0; i < this.formals.size(); i++) {
			if (!this.getFormals().get(i).getType().getDisplayName().equals(
					method.getFormals().get(i).getType().getDisplayName()))
					return (false);
		}
		
		return (true);
	}
	
	public String getId() {
		return (String.format("%s_%s", 
				this.getScope().currentClass().getName(),
				this.getName()));
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		for (Parameter param : this.formals) {
			builder.append(param.getType().toString() + ", ");
		}
		
		if (!this.formals.isEmpty())
			builder.delete(builder.length() - 2, builder.length());
		
		return (String.format("%s : %s -> %s", 
				this.getName(), builder.toString(), this.type));
	}
}
