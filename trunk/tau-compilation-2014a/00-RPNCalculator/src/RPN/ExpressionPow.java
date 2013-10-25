/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

public class ExpressionPow extends ExpressionAction {
	
	public ExpressionPow(ExpressionNode left, ExpressionNode right) {
		super(left, right, '^');
	}

	@Override
	public double getValue() {
		return (Math.pow(this.left.getValue(), this.right.getValue()));
	}
}
