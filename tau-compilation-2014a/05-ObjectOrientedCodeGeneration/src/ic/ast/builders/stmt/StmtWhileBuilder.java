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
import ic.ast.stmt.Statement;
import ic.ast.stmt.StmtWhile;

public class StmtWhileBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		Expression condition = 
				buildHelper.build(parseTree.subtrees.get(2), Expression.class);
		
		Statement operation = 
				buildHelper.build(parseTree.subtrees.get(4), Statement.class);
		
		return (new StmtWhile(condition, operation));
	}
	
	@Override
	public String getParseTreeTag() {
		return ("STMTWHILE");
	}
}
