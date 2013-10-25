/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

public class ExpressionAdd extends ExpressionAction {
	
	public ExpressionAdd(ExpressionNode left, ExpressionNode right) {
		super(left, right, '+');
	}

	@Override
	public double getValue() {
		return (this.left.getValue() + this.right.getValue());
	}
}
