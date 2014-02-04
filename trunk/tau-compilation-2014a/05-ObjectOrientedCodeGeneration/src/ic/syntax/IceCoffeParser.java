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
import ic.ast.builders.ASTBuilder;
import ic.ast.decl.Program;
import ic.lexer.Lexer;
import ic.lexer.LexicalException;
import ic.lexer.Token;

import java.io.IOException;
import java.util.List;

public class IceCoffeParser {

	private List<Token> programTokens;
	private List<Token> libraryTokens;

	public IceCoffeParser(String programFile, String libraryFile)
			throws LexicalException, IOException {
		this(Lexer.getFileTokens(programFile), Lexer.getFileTokens(libraryFile));
	}

	public IceCoffeParser(List<Token> programTokens, List<Token> libraryTokens) {
		this.programTokens = programTokens;
		this.libraryTokens = libraryTokens;
	}

	private Program internalParse(List<Token> tokens, String grammer) {
		if (tokens != null) {
			// Parse the tokens into an parse tree using early algorithm
			Grammar icGrammer = new Grammar(grammer);
			EarleyParser icParser = new EarleyParser(tokens, icGrammer);

			List<EarleyState> completedParses = icParser.getCompletedParses();

			if (completedParses.isEmpty()) {
				throw new SyntaxException(icParser.diagnoseError());
			} else {

				Tree parseTree = completedParses.get(0).parseTree();

				// Build an AST from the parse tree
				Program programAST = ASTBuilder.getBuilder().build(
						parseTree.subtrees.get(0), Program.class);

				return (programAST);
			}
		}

		return (null);
	}

	public Program parse() throws IceCoffeException, IOException {
		
		Program prog = this.internalParse(this.programTokens, 
				IceCoffeGrammers.getIceCoffeGrammer(false));
		Program lib = this.internalParse(this.libraryTokens, 
				IceCoffeGrammers.getIceCoffeGrammer(true));
		
		// If library exist combine it into the program
		if (lib != null)
			prog.getClasses().addAll(0, lib.getClasses());
		
		return (prog);
	}

}
