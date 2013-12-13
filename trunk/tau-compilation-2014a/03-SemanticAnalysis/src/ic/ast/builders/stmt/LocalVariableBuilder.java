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
import ic.ast.decl.Type;
import ic.ast.expr.Expression;
import ic.ast.stmt.LocalVariable;
import ic.lexer.Token;
import ic.syntax.SyntaxException;

public class LocalVariableBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		int line = ((Token)parseTree.subtrees.get(1).root).line;
		Type type = buildHelper.build(parseTree.subtrees.get(0), Type.class);
		String name = ((Token)parseTree.subtrees.get(1).root).text;
		
		Expression expr = parseTree.subtrees.get(2).root.tag.equals("=") ?
				 buildHelper.build(parseTree.subtrees.get(3), Expression.class) : null;
		
		return (new LocalVariable(line, type, name, expr));
		
	}

	@Override
	public String getParseTreeTag() {
		return ("STMTLOCALVAR");
	}
}
