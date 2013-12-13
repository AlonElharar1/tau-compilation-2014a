/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.syntax;

import fun.grammar.Grammar;
import fun.parser.Tree;
import fun.parser.earley.EarleyParser;
import fun.parser.earley.EarleyState;
import ic.IceCoffeException;
import ic.IceCoffeGrammers;
import ic.ast.builders.ASTBuilder;
import ic.ast.decl.Program;
import ic.lexer.Lexer;
import ic.lexer.Token;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class IceCoffeParser {

	List<Token> tokens;
	public boolean isLibrary;
	
	public IceCoffeParser(List<Token> tokens, boolean isLibrary) {
		this.tokens = tokens;
		this.isLibrary = isLibrary;
	}
	
	public Program parse() throws IceCoffeException, IOException {
		
		if (tokens != null) {
			// Parse the tokens into an parse tree using early algorithm
			Grammar icGrammer = new Grammar(isLibrary ? 
					IceCoffeGrammers.IC_LIB_CFG : IceCoffeGrammers.IC_CFG);
			EarleyParser icParser = new EarleyParser(tokens, icGrammer);
			
			List<EarleyState> completedParses = icParser.getCompletedParses();
			
			if (completedParses.isEmpty()) {
				throw new SyntaxException(icParser.diagnoseError());
			}
			else {
				
				Tree parseTree = completedParses.get(0).parseTree();
				
				// Build an AST from the parse tree
				Program programAST = ASTBuilder.getBuilder().build(
						parseTree.subtrees.get(0),
						Program.class);
				
				return (programAST);
			}
		}
		
		return (null);
	}
	
}
