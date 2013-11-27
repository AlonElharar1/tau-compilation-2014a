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
import ic.ast.stmt.StmtBreak;
import ic.lexer.Token;

public class StmtBreakBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) {
		return (new StmtBreak(((Token)parseTree.subtrees.get(0).root).line));
	}
	
	@Override
	public String getParseTreeTag() {
		return ("STMTBREAK");
	}
}
