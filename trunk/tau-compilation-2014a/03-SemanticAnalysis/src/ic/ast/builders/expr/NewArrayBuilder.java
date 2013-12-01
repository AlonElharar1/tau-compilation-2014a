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
import ic.ast.decl.Type;
import ic.ast.expr.Expression;
import ic.ast.expr.NewArray;

public class NewArrayBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		Type type = 
				buildHelper.build(parseTree.subtrees.get(1), Type.class);
		Expression exprSize = 
				buildHelper.build(parseTree.subtrees.get(3), Expression.class);
		
		return (new NewArray(type, exprSize));
	}

	@Override
	public String getParseTreeTag() {
		return ("EXPRNEWARRAY");
	}
}
