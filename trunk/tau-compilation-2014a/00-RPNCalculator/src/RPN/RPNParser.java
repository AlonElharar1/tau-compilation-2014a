/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

import java.util.Stack;

/**
 * Represents a Reverse Polish notation parser 
 */
public class RPNParser {

	private String expression;
	private ExpressionNode expressionRoot;
	
	/**
	 * Creates a new parser
	 * @param expression The expression to associate with this parser
	 */
	public RPNParser(String expression) {
		this.expression = expression;
	}

	/**
	 * Parse the expression
	 */
	public void parse() throws Exception {
	
		this.expressionRoot = null;
		
		Stack<ExpressionNode> stack = new Stack<ExpressionNode>();
		stack.empty();
		
		// Create a tree of the tokens
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
		
		// At this point, only one token should be left at the stack if the expression is valid
		if (stack.size() > 1) {
			throw new Exception("Expression is invalid. Stack has more then one items.");
		}
		else if	(!stack.isEmpty()) {
			this.expressionRoot = stack.pop();
		}
	}
	
	/**
	 * Gets the expression infix string representation
	 * @return
	 */
	public String toInfixString() {
		
		if(this.expressionRoot == null)
			return ("");
		
		return (this.expressionRoot.toInfixString());
	}
	
	/**
	 * Gets the expression value
	 * @return
	 */
	public double evaluate() {
		
		if (this.expressionRoot == null)
			return (0);
		
		return (this.expressionRoot.getValue());
	}
	
}
