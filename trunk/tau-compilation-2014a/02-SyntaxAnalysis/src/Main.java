/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import fun.grammar.Grammar;
import fun.parser.Tree;
import fun.parser.earley.EarleyParser;
import fun.parser.earley.EarleyState;
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

		try {
			
			// Open the file and extract tokens
			fileStream = new FileInputStream(args[0]);
			List<Token> tokens = new Lexer(fileStream).getAllTokens();
			
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
					
					// TODO Convert the parsing tree into an AST
				}
			}
			
			// TODO Print the AST
			

		} catch (LexicalException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Error reading file...");
		} finally {
			if (fileStream != null)
				fileStream.close();
		}
	}

}
