/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

/**
 * Represents an expression tree node
 */
public abstract class ExpressionNode {

	public ExpressionNode left;
	public ExpressionNode right;
	
	/**
	 * Creates a new expression node 
	 * @param left
	 * @param right
	 */
	public ExpressionNode(ExpressionNode left, ExpressionNode right) {
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Gets whether this node is a leaf or not
	 * @return
	 */
	public boolean isLeaf() {
		return ((this.left == null) && (this.right == null));
	}
	
	/**
	 * Gets the current node value
	 * @return
	 */
	public abstract double getValue();
	
	/**
	 * Gets the current node infix string representation
	 * @return
	 */
	public abstract String toInfixString();
	
}
