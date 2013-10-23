/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

import java.util.ArrayList;
import java.util.List;

public class RPNConverter {

	private class Tree<T> {
	    private Node<T> root;

	    public Tree(T rootData) {
	        root = new Node<T>();
	        root.data = rootData;
	        root.children = new ArrayList<Node<T>>();
	    }

	    public class Node<T> {
	        private T data;
	        private Node<T> parent;
	        private List<Node<T>> children;
	    }
	}
	
	public String toInfix(String expression) {
		
		Tree<String> root;
		
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
		
		
		return null;
	}
	
}
