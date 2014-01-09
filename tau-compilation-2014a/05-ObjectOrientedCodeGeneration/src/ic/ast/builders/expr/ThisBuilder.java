/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.ast.builders.expr;

import fun.parser.Tree;
import ic.ast.Node;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.expr.This;
import ic.lexer.Token;

public class ThisBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) {
		
		return (new This(((Token)parseTree.subtrees.get(0).root).line));
		
	}

	@Override
	public String getParseTreeTag() {
		return ("EXPRTHIS");
	}
}
