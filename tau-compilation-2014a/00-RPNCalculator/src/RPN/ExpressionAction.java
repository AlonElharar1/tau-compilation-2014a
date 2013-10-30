/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

/**
 * Represents an expression node for an action
 */
public abstract class ExpressionAction extends ExpressionNode {
	
	private char actionChar;
	
	public ExpressionAction(ExpressionNode left, ExpressionNode right, 
			char actionChar) {
		super(left, right);
		this.actionChar = actionChar;
	}
	
	@Override
	public String toInfixString() {
		
		// Concatenate both children with the action char
		String format = 
				(this.left.isLeaf() ? "%s" : "(%s)") + 
				this.actionChar +
				(this.right.isLeaf() ? "%s" : "(%s)"); 
				
		return (String.format(format, 
					left.toInfixString(),
					right.toInfixString()));
	}
	
}
