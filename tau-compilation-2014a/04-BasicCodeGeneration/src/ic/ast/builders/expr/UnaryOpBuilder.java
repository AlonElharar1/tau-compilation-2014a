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
import ic.ast.expr.UnaryOp;
import ic.lexer.Token;

public class UnaryOpBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		Token operatorToken = (Token)parseTree.subtrees.get(0).subtrees.get(0).root;
		Expression operand = 
				buildHelper.build(parseTree.subtrees.get(1), Expression.class);

		return (new UnaryOp(operatorToken.line,
				UnaryOp.UnaryOps.find(operatorToken.text), operand));
	}

	@Override
	public String getParseTreeTag() {
		return ("EXPRUNOP");
	}
}
