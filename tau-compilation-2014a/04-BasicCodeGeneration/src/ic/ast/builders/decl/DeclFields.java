package ic.ast.builders.decl;

import java.util.ArrayList;
import java.util.List;

import ic.ast.Visitor;
import ic.ast.decl.DeclField;
import ic.ast.decl.Type;

/**
 * Class field AST node.
 * 
 */
public class DeclFields extends DeclField {


	private List<String> names;
	
	
	
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new fields node.
	 * 
	 * @param type
	 *            Data type of field.
	 * @param names
	 *            List of names of different fields.
	 */
	public DeclFields(Type type, List<String> names)
	{
		super(type, null);
		this.names = names;
	}
	

	public List<String> getNames() {
		return this.names;
	}
	
	public List<DeclField> seperate()
	{
		List<DeclField> fields = new ArrayList<DeclField>();
		for(String name : this.names)
		{
			fields.add(new DeclField(this.getType(), name));
		}
		return fields;
	}

}
