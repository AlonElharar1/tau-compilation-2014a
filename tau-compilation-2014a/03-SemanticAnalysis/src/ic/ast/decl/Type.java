package ic.ast.decl;

import java.util.Arrays;

import ic.ast.Node;

/**
 * Abstract base class for data type AST nodes.
 * 
 */
public abstract class Type extends Node {

	/**
	 * Number of array indexes in data type. 
	 * For example, int[][] -> dimension = 2.
	 */
	private int dimension = 0;

	/**
	 * Constructs a new type node. Used by subclasses.
	 * 
	 * @param line
	 *            Line number of type declaration.
	 */
	protected Type(int line) {
		super(line);
	}

	public abstract String getDisplayName();

	public int getArrayDimension() {
		return dimension;
	}
	
	public void setArrayDimension(int dimension) {
		this.dimension = dimension;
	}

	public void incrementDimension() {
		++dimension;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String typeStr = this.getDisplayName();
		
		for (int i = 0; i < this.dimension; i++) {
			typeStr += "[]";
		}
		
		return (typeStr);
	}
}
