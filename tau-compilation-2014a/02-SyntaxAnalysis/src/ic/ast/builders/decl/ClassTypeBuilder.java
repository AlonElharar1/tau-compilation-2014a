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
import ic.ast.decl.ClassType;
import ic.lexer.Token;

public class ClassTypeBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) {
	
		Token classidToken = (Token)parseTree.subtrees.get(0).root;
		
		return (new ClassType(classidToken.line, classidToken.text));
	}

	@Override
	public String getParseTreeTag() {
		return ("CLASS_TYPE");
	}

}
