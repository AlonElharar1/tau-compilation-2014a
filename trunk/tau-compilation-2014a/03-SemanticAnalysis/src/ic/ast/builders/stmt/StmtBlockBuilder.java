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
import ic.ast.stmt.Statement;
import ic.ast.stmt.StmtBlock;
import ic.lexer.Token;
import ic.syntax.SyntaxException;

import java.util.List;

public class StmtBlockBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		int line = ((Token)parseTree.subtrees.get(0).root).line;
		
		List<Statement> statements = buildHelper.depthBuild(
				parseTree.subtrees.get(1), 
				new String[] { "STMTS" }, 
				new String[] { "STMT" }, 
				Statement.class);
		
		return (new StmtBlock(line, statements));
	}
	
	@Override
	public String getParseTreeTag() {
		return ("STMTBLOCK");
	}
}
