/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.ast.builders.stmt;

import fun.parser.Tree;
import ic.ast.Node;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.expr.Expression;
import ic.ast.expr.Ref;
import ic.ast.stmt.StmtAssignment;
import ic.syntax.SyntaxException;

public class StmtAssignmentBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		Ref location = buildHelper.build(parseTree.subtrees.get(0), Ref.class);
		Expression expr = buildHelper.build(parseTree.subtrees.get(2), Expression.class);
		
		return (new StmtAssignment(location, expr));
	}
	
	@Override
	public String getParseTreeTag() {
		return ("STMTASSIGN");
	}
}
