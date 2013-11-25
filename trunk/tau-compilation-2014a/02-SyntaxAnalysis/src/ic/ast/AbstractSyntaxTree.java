/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.ast;

import fun.parser.Tree;

public class AbstractSyntaxTree {

	private Node head;
	
	public AbstractSyntaxTree(Tree parseTree) {
		this.createFromParseTree(parseTree);
	}

	public Node getHead() {
		return (this.head);
	}
	
	private void createFromParseTree(Tree parseTree) {
		
	}
	
}
