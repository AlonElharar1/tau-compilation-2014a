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
import ic.ast.expr.Expression;
import ic.ast.expr.RefField;
import ic.lexer.Token;
import ic.syntax.SyntaxException;

public class RefFieldBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {

		Expression object = 
				buildHelper.build(parseTree.subtrees.get(0), Expression.class);
		Token fieldName = (Token)parseTree.subtrees.get(2).root;
		
		return (new RefField(fieldName.line, object, fieldName.text));
		
	}

	@Override
	public String getParseTreeTag() {
		return ("REFFIELD");
	}
}
