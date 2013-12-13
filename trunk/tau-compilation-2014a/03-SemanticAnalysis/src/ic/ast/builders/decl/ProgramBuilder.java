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
import ic.ast.decl.DeclClass;
import ic.ast.decl.Program;
import ic.syntax.SyntaxException;

public class ProgramBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		return (new Program(buildHelper.depthBuild(parseTree, 
					new String[] { "Program" }, 
					new String[] { "CLASS", "CLASSEXT" }, 
					DeclClass.class)));
		
	}

	@Override
	public String getParseTreeTag() {
		return ("Program");
	}
}
