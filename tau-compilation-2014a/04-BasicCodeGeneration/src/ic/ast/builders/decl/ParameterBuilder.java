/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.ast.builders.decl;

import fun.parser.Tree;
import ic.ast.Node;
import ic.syntax.SyntaxException;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.decl.Parameter;
import ic.ast.decl.Type;
import ic.lexer.Token;

public class ParameterBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		return (new Parameter(
				buildHelper.build(parseTree.subtrees.get(0), Type.class), 
				((Token)parseTree.subtrees.get(1).root).text));
		
	}

	@Override
	public String getParseTreeTag() {
		return ("PARAMETER");
	}
	
}
