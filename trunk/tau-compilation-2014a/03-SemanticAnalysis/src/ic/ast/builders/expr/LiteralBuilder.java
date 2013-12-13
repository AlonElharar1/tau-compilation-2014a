/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.ast.builders.expr;

import fun.parser.Tree;
import ic.ast.Node;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.decl.PrimitiveType;
import ic.ast.expr.Literal;
import ic.lexer.Token;
import ic.syntax.SyntaxException;

public class LiteralBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		Token literalToken = (Token)parseTree.subtrees.get(0).root;
		
		try {
			
			if (literalToken.tag.equals("INTEGER")) {
				return (new Literal(literalToken.line,
						PrimitiveType.DataType.INT, 
						Integer.parseInt(literalToken.text)));
			}
			else if (literalToken.tag.equals("STRING")) {
				return (new Literal(literalToken.line, 
						PrimitiveType.DataType.STRING, 
						literalToken.text.substring(1, literalToken.text.length() - 1)));
			}
			else if (literalToken.tag.equals("true") || literalToken.tag.equals("false")) {
				return (new Literal(literalToken.line, 
						PrimitiveType.DataType.BOOLEAN, 
						literalToken.text.equals("true") ? true : false));
			}
			else if (literalToken.tag.equals("null")) {
				return (new Literal(literalToken.line, 
						PrimitiveType.DataType.VOID, 
						null));
			}
			
		} catch (NumberFormatException e) {
			throw new SyntaxException(literalToken.line, literalToken.column, 
					"numeric literal out of range: " + literalToken.text);
		}
			
		throw new Error("internal error; invalid literal: " + literalToken.text);
	}

	@Override
	public String getParseTreeTag() {
		return ("LITERAL");
	}
}
