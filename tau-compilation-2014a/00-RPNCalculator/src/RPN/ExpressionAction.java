/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

public abstract class ExpressionAction extends ExpressionNode {
	
	private char actionChar;
	
	public ExpressionAction(ExpressionNode left, ExpressionNode right, 
			char actionChar) {
		super(left, right);
		this.actionChar = actionChar;
	}
	
	@Override
	public String toInfixString() {
		return (String.format("( %s %c %s )", 
					left.toInfixString(),
					this.actionChar, 
					right.toInfixString()));
	}
}
