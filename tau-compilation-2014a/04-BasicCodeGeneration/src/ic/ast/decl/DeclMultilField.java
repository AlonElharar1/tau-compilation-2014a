/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.ast.decl;

import java.util.ArrayList;
import java.util.List;

import ic.ast.Node;
import ic.ast.Visitor;

/**
 * Class multi-field AST node.
 * 
 */
public class DeclMultilField extends Node {

	private Type type;
	private List<DeclField> fields = new ArrayList<DeclField>();
	
	public Object accept(Visitor visitor) {
		return null;
	}

	/**
	 * Constructs a new fields node.
	 * 
	 * @param type
	 *            Data type of fields.
	 * @param ids
	 *            The ids of the fields
	 */
	public DeclMultilField(Type type, Iterable<String> ids)
	{
		super(type.getLine());
		this.type = type;
		
		for (String id : ids) {
			this.fields.add(new DeclField(type, id));
		}
	}
	
	public Type getType() {
		return type;
	}
	
	public List<DeclField> getFields() {
		return (this.fields);
	}

}
