/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

import java.util.Stack;

public class RPNParser {

	private String expression;
	private ExpressionNode expressionRoot;
	
	public RPNParser(String expression) {
		this.expression = expression;
	}

	public void parse() throws Exception {
	
		Stack<ExpressionNode> stack = new Stack<ExpressionNode>();
		stack.empty();
		
		for (String token : expression.split("\\s+")) {
			
			if ((token.charAt(0) >= '0') &&
				(token.charAt(0) <= '9')) {
				stack.push(new ExpressionNumber(token));
			}
			else if (token.equals("+")) {
				ExpressionNode right = stack.pop();
				stack.push(new ExpressionAdd(
						stack.pop(), right));
			}
			else if (token.equals("-")) {
				ExpressionNode right = stack.pop();
				stack.push(new ExpressionSub(
						stack.pop(), right));
			}
			else if (token.equals("*")) {
				ExpressionNode right = stack.pop();
				stack.push(new ExpressionMul(
						stack.pop(), right));
			}
			else if (token.equals("/")) {
				ExpressionNode right = stack.pop();
				stack.push(new ExpressionDiv(
						stack.pop(), right));
			}
			else if (token.equals("^")) {
				ExpressionNode right = stack.pop();
				stack.push(new ExpressionPow(
						stack.pop(), right));
			}
			else {
				throw new Exception("Unkown token!");
			}
		}
		
		if(stack.isEmpty())
		{
			this.expressionRoot = null;
		}
		else
		{
			//At this point, only one token should be left at the stack.
			ExpressionNode finalToken = stack.pop();
			if(finalToken instanceof ExpressionAction)
			{
				((ExpressionAction) finalToken).setRoot();
			}
			else
			{
				throw new Exception("Final token must be an expression!");
			}
			this.expressionRoot = stack.isEmpty() ? finalToken : null;
		}
		
		
	}
	
	public String toInfixString() {
		if(this.expressionRoot != null)
			return (this.expressionRoot.toInfixString());
		
		return "";
	}
	
	public double evaluate() throws Exception {
		if (this.expressionRoot == null)
			throw new Exception("The root is null!");
		
		return (this.expressionRoot.getValue());
	}
	
}
