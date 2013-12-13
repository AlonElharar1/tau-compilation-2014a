/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.ast.builders.decl;

import fun.parser.Tree;
import ic.ast.Node;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.decl.DeclField;
import ic.ast.decl.Type;
import ic.lexer.Token;
import ic.syntax.SyntaxException;

public class DeclFieldBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		return (new DeclField(
				buildHelper.build(parseTree.subtrees.get(0), Type.class), 
				((Token)parseTree.subtrees.get(1).root).text));
		
	}

	@Override
	public String getParseTreeTag() {
		return ("FIELD");
	}
	
}
