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
import ic.ast.expr.StaticCall;
import ic.lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class StaticCallBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		Token classId = (Token)parseTree.subtrees.get(0).root;
		Token methodName = (Token)parseTree.subtrees.get(2).root;
		
		List<Expression> args = parseTree.subtrees.size() < 6 ?
				new ArrayList<Expression>() : buildHelper.depthBuild(
						parseTree.subtrees.get(4), 
						new String[] { "EXPRS" }, 
						new String[] { "EXPR" }, 
						Expression.class);
		
		return (new StaticCall(classId.line, classId.text, methodName.text, args));
	}
	
	@Override
	public String getParseTreeTag() {
		return ("STATIC_CALL");
	}

}
