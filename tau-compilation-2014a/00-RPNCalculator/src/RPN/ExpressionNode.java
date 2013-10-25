/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

public abstract class ExpressionNode {

	public ExpressionNode left;
	public ExpressionNode right;
	
	public ExpressionNode(ExpressionNode left, ExpressionNode right) {
		this.left = left;
		this.right = right;
	}
	
	public abstract double getValue();
	
	public abstract String toInfixString();
	
}
