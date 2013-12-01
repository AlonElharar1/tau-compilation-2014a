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
import ic.ast.expr.RefVariable;
import ic.lexer.Token;

public class RefVariableBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) {
	
		Token varName = (Token)parseTree.subtrees.get(0).root;
		
		return (new RefVariable(varName.line, varName.text));
		
	}

	@Override
	public String getParseTreeTag() {
		return ("REFVAR");
	}
}
