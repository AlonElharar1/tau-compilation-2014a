/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.ast.builders;

import fun.parser.Tree;
import ic.ast.Node;
import ic.syntax.SyntaxException;

public interface ASTNodeBuilder {

	public String getParseTreeTag();
	
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException;
}
