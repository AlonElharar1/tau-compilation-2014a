System.out.println(yourAst.accept(new PrettyPrint()));/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import fun.grammar.Grammar;
import fun.parser.Tree;
import fun.parser.earley.EarleyParser;
import fun.parser.earley.EarleyState;
import ic.ast.AbstractSyntaxTree;
import ic.ast.PrettyPrint;
import ic.lexer.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {

	public static final String ICE_COFFE_CFG_FILE = "ICCFG.cfg";
	
	public static void main(String[] args) throws IOException {

		// Validate arguments
		if (args.length < 1) {
			System.out.println("Not cool man... I need a file!");
			return;
		}

		FileInputStream fileStream = null;
		List<Token> tokens = null;
		
		// Open the file and extract tokens
		try {
			
			fileStream = new FileInputStream(args[0]);
			tokens = new Lexer(fileStream).getAllTokens();
			fileStream.close();

		} catch (LexicalException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Error reading file...");
		} finally {
			if (fileStream != null)
				fileStream.close();
		}
		
		if (tokens != null) {
			
			// Parse the tokens into an parse tree using early algorithm
			Grammar icGrammer = new Grammar(new File(ICE_COFFE_CFG_FILE));
			EarleyParser icParser = new EarleyParser(tokens, icGrammer);
			
			List<EarleyState> completedParses = icParser.getCompletedParses();
			
			if (completedParses.isEmpty()) {
				// TODO Error handling
			}
			else {
				
				for (EarleyState earleyState : completedParses) {
					Tree currTree = earleyState.parseTree();
					
					// Convert the parsing tree into an AST
					AbstractSyntaxTree ast = new AbstractSyntaxTree(currTree);
					//print the AST in human-readable representation format using PrettyPrint Visitor
					System.out.println(yourAst.accept(new PrettyPrint()));				
        }
			}
		}
	}

}
