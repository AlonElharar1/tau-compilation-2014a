package ic.ast.builders.stmt;


import fun.parser.Tree;
import ic.ast.Node;
import ic.syntax.SyntaxException;
import ic.ast.builders.ASTBuilder;
import ic.ast.builders.ASTNodeBuilder;
import ic.ast.expr.Expression;
import ic.ast.stmt.Statement;
import ic.ast.stmt.StmtIf;

public class StmtMatchedIfBuilder implements ASTNodeBuilder {

	@Override
	public Node Build(Tree parseTree, ASTBuilder buildHelper) throws SyntaxException {
		
		if (parseTree.subtrees.size() == 1)	{
			return (buildHelper.build(parseTree.subtrees.get(0)));
		}
		
		Expression condition = 
				buildHelper.build(parseTree.subtrees.get(2), Expression.class);
		
		Statement operation = 
				buildHelper.build(parseTree.subtrees.get(4), Statement.class);
		
		Statement elseOperation = 
				buildHelper.build(parseTree.subtrees.get(6), Statement.class);
				
		return (new StmtIf(condition, operation, elseOperation));
	}
	
	@Override
	public String getParseTreeTag() {
		return ("MATCHED_IF");
	}
}
