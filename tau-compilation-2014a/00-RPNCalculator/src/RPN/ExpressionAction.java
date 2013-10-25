/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

public abstract class ExpressionAction extends ExpressionNode {
	
	public ExpressionAction(ExpressionNode left, ExpressionNode right) {
		super(left, right);
	}
	
}
