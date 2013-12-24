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
import ic.ast.expr.RefArrayElement;

public class RefArrayElementBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		Expression array = 
				buildHelper.build(parseTree.subtrees.get(0), Expression.class);
		Expression index = 
				buildHelper.build(parseTree.subtrees.get(2), Expression.class);
		
		return (new RefArrayElement(array, index));
		
	}
	
	@Override
	public String getParseTreeTag() {
		return ("REFARRAY");
	}
	
}
