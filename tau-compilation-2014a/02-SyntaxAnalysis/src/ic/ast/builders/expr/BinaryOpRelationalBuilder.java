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
import ic.ast.expr.BinaryOp;
import ic.ast.expr.Expression;
import ic.lexer.Token;

public class BinaryOpRelationalBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		if (parseTree.subtrees.size() == 1)
		{
			return buildHelper.build(parseTree.subtrees.get(0), Expression.class);
		}
		else
		{
			Expression operand1 = 
					buildHelper.build(parseTree.subtrees.get(0), Expression.class);
			
			Token operatorToken = (Token)parseTree.subtrees.get(1).subtrees.get(0).root;
			BinaryOp.BinaryOps operator =
					BinaryOp.BinaryOps.find(operatorToken.text);
			
			Expression operand2 = 
					buildHelper.build(parseTree.subtrees.get(2), Expression.class);
			
			return (new BinaryOp(operatorToken.line, operand1, operator, operand2));
		}
	}
	
	@Override
	public String getParseTreeTag() {
		return ("EXPRBINOP_RELATIONAL");
	}
}
