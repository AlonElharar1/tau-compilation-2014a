/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

import java.util.Stack;

public class RPNCalculator {

	private String expression;
	private Stack<Double> stack = new Stack<Double>();
	
	public RPNCalculator(String expression) {
		this.expression = expression;
		stack.empty();
	}

	public void parse() {
		
	}
	
	public String toInfix() {	
		return null;
	}
	
	public double evaluate() throws Exception {
		
		for (String token : expression.split("\\s+")) {
			
			if ((token.charAt(0) >= '0') &&
				(token.charAt(0) <= '9')) {
				stack.push(Double.parseDouble(token));
			}
			else if (token.equals("+")) {
				stack.push(stack.pop() + stack.pop());
			}
			else if (token.equals("-")) {
				Double num = stack.pop();
				stack.push(stack.pop() - num);
			}
			else if (token.equals("*")) {
				stack.push(stack.pop() + stack.pop());
			}
			else if (token.equals("/")) {
				Double num = stack.pop();
				stack.push(stack.pop() / num);
			}
			else if (token.equals("^")) {
				stack.push(Math.pow(stack.pop(), stack.pop()));
			}
			else {
				throw new Exception("Unkown token!");
			}
			
		}
		
		return (stack.pop());
	}
	
}
