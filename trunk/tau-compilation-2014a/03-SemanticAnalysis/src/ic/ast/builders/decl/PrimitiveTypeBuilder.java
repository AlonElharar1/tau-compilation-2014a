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
import ic.ast.decl.PrimitiveType;
import ic.lexer.Token;

public class PrimitiveTypeBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) {
		
		Token typeToken = (Token)parseTree.subtrees.get(0).root;
		
		return (new PrimitiveType(typeToken.line,
				PrimitiveType.DataType.find(typeToken.text)));
		
	}

	@Override
	public String getParseTreeTag() {
		return ("PRIMITIVE");
	}
}
