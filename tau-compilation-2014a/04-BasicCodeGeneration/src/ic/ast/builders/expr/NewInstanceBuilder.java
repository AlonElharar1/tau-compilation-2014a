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
import ic.ast.expr.NewInstance;
import ic.lexer.Token;

public class NewInstanceBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) {
		
		Token classId = (Token)parseTree.subtrees.get(1).root;
		
		return (new NewInstance(classId.line, classId.text));
	}
	
	@Override
	public String getParseTreeTag() {
		return ("EXPRNEWINS");
	}
	
}
