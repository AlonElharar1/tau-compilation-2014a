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
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclFields;
import ic.ast.decl.DeclMethod;
import ic.lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class DeclClassBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		Token nameToken = (Token)parseTree.subtrees.get(1).root;
		
		String superClassName = 
				parseTree.subtrees.get(2).root.tag.equals("extends") ? 
						((Token)parseTree.subtrees.get(3).root).text : null;
		
		List<DeclField> fieldsOfFields = buildHelper.depthBuild(parseTree, 
				new String[] { "CLASS", "CLASS_CONTENT" }, 
				new String[] { "FIELD" }, 
				DeclField.class);
		
		List<DeclField> fields = new ArrayList<DeclField>();
		
		for(DeclField field : fieldsOfFields)
		{
			if(field instanceof DeclFields)
			{
				fields.addAll(((DeclFields) field).seperate());
			}
			else
			{
				fields.add(field);
			}
		}
		
		List<DeclMethod> methods = buildHelper.depthBuild(parseTree, 
				new String[] { "CLASS", "CLASS_CONTENT" }, 
				new String[] { "METHOD" }, 
				DeclMethod.class);
		
		
		return (new DeclClass(nameToken.line, nameToken.text, superClassName, fields, methods));
				
	}

	@Override
	public String getParseTreeTag() {
		return ("CLASS");
	}
	
}
