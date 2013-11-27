/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.ast.builders.expr;

import fun.parser.Tree;
import ic.ast.Node;
import ic.ast.SyntaxException;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;

public class ExpressionBlockBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		return (buildHelper.build(parseTree.subtrees.get(1)));
	}
	
	@Override
	public String getParseTreeTag() {
		return ("EXPRBLOCK");
	}
}
