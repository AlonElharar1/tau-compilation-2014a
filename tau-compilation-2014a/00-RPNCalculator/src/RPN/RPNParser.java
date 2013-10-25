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
				stack.push(new ExpressionAdd(
						stack.pop(), stack.pop()));
			}
			else if (token.equals("-")) {
				stack.push(new ExpressionSub(
						stack.pop(), stack.pop()));
			}
			else if (token.equals("*")) {
				stack.push(new ExpressionMul(
						stack.pop(), stack.pop()));
			}
			else if (token.equals("/")) {
				stack.push(new ExpressionDiv(
						stack.pop(), stack.pop()));
			}
			else if (token.equals("^")) {
				stack.push(new ExpressionPow(
						stack.pop(), stack.pop()));
			}
			else {
				throw new Exception("Unkown token!");
			}
		}
	
		this.expressionRoot = stack.isEmpty() ? null : stack.pop();
	}
	
	public String toInfixString() {	
		return (this.expressionRoot.toInfixString());
	}
	
	public double evaluate() {
		if (this.expressionRoot == null)
			return (Double.NaN);
		
		return (this.expressionRoot.getValue());
	}
	
}
