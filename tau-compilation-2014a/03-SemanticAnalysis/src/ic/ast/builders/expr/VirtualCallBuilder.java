/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.ast.builders.expr;

import fun.parser.Tree;
import ic.ast.Node;
import ic.ast.SyntaxException;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.expr.Expression;
import ic.ast.expr.VirtualCall;
import ic.lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class VirtualCallBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		Expression object = null;
		Token methodName = null;
		
		if (!parseTree.subtrees.get(0).root.tag.equals("ID")) {
			object = buildHelper.build(parseTree.subtrees.get(0), Expression.class);
			methodName = (Token)parseTree.subtrees.get(2).root;
		}
		else {
			methodName = (Token)parseTree.subtrees.get(0).root;
		}
		
		List<Expression> args = 
				!parseTree.subtrees.get(parseTree.subtrees.size() - 2).root.tag.equals("EXPRS") ?
					new ArrayList<Expression>() : buildHelper.depthBuild(
							parseTree.subtrees.get(parseTree.subtrees.size() - 2), 
							new String[] { "EXPRS" }, 
							new String[] { "EXPR" }, 
							Expression.class);
		
		return (new VirtualCall(methodName.line, object, methodName.text, args));
	}
	
	@Override
	public String getParseTreeTag() {
		return ("VIRTUAL_CALL");
	}

}
