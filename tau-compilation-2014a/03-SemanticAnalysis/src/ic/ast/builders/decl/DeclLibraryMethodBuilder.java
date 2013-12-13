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
import ic.ast.decl.*;
import ic.lexer.Token;
import ic.syntax.SyntaxException;

import java.util.List;

public class DeclLibraryMethodBuilder implements ASTNodeBuilder {

	@Override
	public String getParseTreeTag() {
		return ("METHODLIB");
	}

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper)
			throws SyntaxException {
		
		Tree methodHeaderTree = parseTree.subtrees.get(1);
		
		Type returnType = buildHelper.build(methodHeaderTree.subtrees.get(0), Type.class);
		String name = ((Token)methodHeaderTree.subtrees.get(1).root).text;
		
		List<Parameter> parameters = buildHelper.depthBuild(
				methodHeaderTree.subtrees.get(2), 
				new String[] { "METHOD_PARAMS", "FORMALS" }, 
				new String[] { "PARAMETER" }, 
				Parameter.class);
		
		return (new DeclLibraryMethod(returnType, name, parameters));
	}

}
