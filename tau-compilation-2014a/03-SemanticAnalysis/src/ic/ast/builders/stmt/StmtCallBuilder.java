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
import ic.ast.expr.Call;
import ic.ast.stmt.StmtCall;
import ic.syntax.SyntaxException;

public class StmtCallBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		return (new StmtCall(buildHelper.build(parseTree.subtrees.get(0), Call.class)));
	}
	
	@Override
	public String getParseTreeTag() {
		return ("STMTCALL");
	}
}
