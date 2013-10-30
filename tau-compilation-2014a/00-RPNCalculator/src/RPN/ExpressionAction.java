/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

public abstract class ExpressionAction extends ExpressionNode {
	
	private char actionChar;
	private boolean isRoot = false;
	
	public ExpressionAction(ExpressionNode left, ExpressionNode right, 
			char actionChar) {
		super(left, right);
		this.actionChar = actionChar;
	}
	
	@Override
	public String toInfixString() {
		String output;
		
		if(isRoot())
		{
			output =  (String.format("%s%c%s", 
					left.toInfixString(),
					this.actionChar, 
					right.toInfixString()));
		}
		else
		{
			output = (String.format("(%s%c%s)", 
					left.toInfixString(),
					this.actionChar, 
					right.toInfixString()));
		}
		
		return output;
	}
	
	public boolean isRoot() {
		return this.isRoot;
	}
	
	public void setRoot() {
		this.isRoot = true; 
	}
	
}
