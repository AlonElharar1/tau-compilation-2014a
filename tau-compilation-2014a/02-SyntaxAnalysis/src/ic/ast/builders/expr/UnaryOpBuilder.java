/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.ast.builders.expr;

import fun.parser.Tree;
import ic.ast.Node;
import ic.ast.SyntaxException;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.decl.PrimitiveType.DataType;
import ic.ast.expr.Expression;
import ic.ast.expr.Literal;
import ic.ast.expr.UnaryOp;
import ic.ast.expr.UnaryOp.UnaryOps;
import ic.lexer.Token;

public class UnaryOpBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		Token operatorToken = (Token)parseTree.subtrees.get(0).subtrees.get(0).root;
		UnaryOps operator = UnaryOp.UnaryOps.find(operatorToken.text);
		Expression operand = 
				buildHelper.build(parseTree.subtrees.get(1), Expression.class);
		
		if (operand instanceof Literal ) {
			
			Literal operandLiteral = (Literal)operand;
			
			if ((operandLiteral.getType() == DataType.BOOLEAN) &&
				(operator == UnaryOps.LNEG)) {
				return (new Literal(
						operandLiteral.getLine(), 
						operandLiteral.getType(), 
						!(Boolean)operandLiteral.getValue()));
			}
			else if ((operandLiteral.getType() == DataType.INT) &&
					 (operator == UnaryOps.UMINUS)) {
				if (((Integer)operandLiteral.getValue()) == Integer.MIN_VALUE) {
					throw new SyntaxException(operatorToken.line, operatorToken.column, 
							"numeric literal out of range: -" +  operandLiteral.getValue().toString());
				}
				else {
					return (new Literal(
							operandLiteral.getLine(), 
							operandLiteral.getType(), 
							-(Integer)operandLiteral.getValue()));
				}
			}
		
		}
		
		return (new UnaryOp(operatorToken.line, operator, operand));
	}

	@Override
	public String getParseTreeTag() {
		return ("EXPRUNOP");
	}
}
