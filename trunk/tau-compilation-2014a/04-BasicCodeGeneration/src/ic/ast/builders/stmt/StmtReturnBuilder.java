/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.ast.builders.stmt;

import fun.parser.Tree;
import ic.ast.Node;
import ic.syntax.SyntaxException;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.expr.Expression;
import ic.ast.stmt.StmtReturn;
import ic.lexer.Token;

public class StmtReturnBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		int line = ((Token)parseTree.subtrees.get(0).root).line;
		
		Expression expr = parseTree.subtrees.get(1).root.tag.equals("EXPR") ? 
			buildHelper.build(parseTree.subtrees.get(1), Expression.class) : null;
		
		return (new StmtReturn(line, expr));
	}
	
	@Override
	public String getParseTreeTag() {
		return ("STMTRETURN");
	}
}
