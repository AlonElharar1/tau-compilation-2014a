/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.ast.builders.decl;

import java.util.ArrayList;
import java.util.List;

import fun.parser.Tree;
import ic.ast.Node;
import ic.syntax.SyntaxException;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclFields;
import ic.ast.decl.Type;
import ic.lexer.Token;

public class DeclFieldBuilder implements ASTNodeBuilder {

	private List<String> names = new ArrayList<String>();
	
	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		
		
		if(parseTree.subtrees.get(1).subtrees.size() > 1)
		{
			this.getNames(parseTree.subtrees.get(1));
			return(new DeclFields(
					buildHelper.build(parseTree.subtrees.get(0), Type.class), 
					this.names));
			
			
		}
		else
		{
			return (new DeclField(
					buildHelper.build(parseTree.subtrees.get(0), Type.class), 
					((Token)parseTree.subtrees.get(1).subtrees.get(0).root).text));
		}
		
	}

	private void getNames(Tree parseTree)
	{	
		this.names.add(((Token)parseTree.subtrees.get(0).root).text);
		if(parseTree.subtrees.size() > 1)
		{
			this.getNames(parseTree.subtrees.get(2));
		}
	}
	
	@Override
	public String getParseTreeTag() {
		return ("FIELD");
	}
	
	
	
}
