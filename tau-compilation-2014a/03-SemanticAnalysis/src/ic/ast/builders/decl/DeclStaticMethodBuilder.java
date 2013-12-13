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
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.Parameter;
import ic.ast.decl.Type;
import ic.ast.stmt.Statement;
import ic.lexer.Token;
import ic.syntax.SyntaxException;

import java.util.List;

public class DeclStaticMethodBuilder implements ASTNodeBuilder {

	@Override
	public String getParseTreeTag() {
		return ("METHODSTATIC");
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
		
		List<Statement> statements = buildHelper.depthBuild(
				methodHeaderTree.subtrees.get(3), 
				new String[] { "METHOD_CONTENT", "STMTS" }, 
				new String[] { "STMT" }, 
				Statement.class);
		
		return (new DeclStaticMethod(returnType, name, parameters, statements));
	}

}
