/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.ast.builders.expr;

import fun.parser.Tree;
import ic.ast.Node;
import ic.syntax.SyntaxException;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.expr.Expression;
import ic.ast.expr.Length;
import ic.lexer.Token;

public class LengthBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		int line = ((Token)parseTree.subtrees.get(1).root).line;
		Expression expr = buildHelper.build(parseTree.subtrees.get(0), Expression.class);
		
		return (new Length(line, expr));
	}

	@Override
	public String getParseTreeTag() {
		return ("EXPRLEN");
	}
}
