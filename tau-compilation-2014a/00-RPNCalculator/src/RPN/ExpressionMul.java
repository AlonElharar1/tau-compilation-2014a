/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

public class ExpressionMul extends ExpressionAction {
	
	public ExpressionMul(ExpressionNode left, ExpressionNode right) {
		super(left, right, '*');
	}

	@Override
	public double getValue() {
		return (this.right.getValue() * this.left.getValue());
	}
}