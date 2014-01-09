/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.ast.builders.decl;


import fun.parser.Tree;
import ic.ast.Node;
import ic.syntax.SyntaxException;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.decl.Type;

public class TypeBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		// Count array dimensions
		int dimentions = 0;
		Tree currSubTree = parseTree.subtrees.get(0);
		
		while (currSubTree.root.tag.equals("TYPE")) {
			++dimentions;
			currSubTree = currSubTree.subtrees.get(0);
		}
		
		Type type = (Type)buildHelper.build(currSubTree);
		type.setArrayDimension(dimentions);
		
		return (type);
	}

	@Override
	public String getParseTreeTag() {
		return ("TYPE");
	}
}
