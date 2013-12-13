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
import ic.IceCoffeException;
import ic.IceCoffeGrammers;
import ic.ast.PrettyPrint;
import ic.ast.builders.ASTBuilder;
import ic.ast.decl.Parameter;
import ic.ast.decl.Program;
import ic.interpreter.IceCoffeInterpreter;
import ic.lexer.Lexer;
import ic.lexer.Token;
import ic.syntax.SyntaxException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {

		// Validate arguments
		if (args.length < 1) {
			System.out.println("Usage: program [-Llib] [method] [parameter1] ... ");
			return;
		}

		int argIndex = 0;
		
		// Extract the program file name
		String programFile = args[argIndex++];
		
		// Extract library file if given
		String libraryFile = ((args.length > 1) && (args[1].startsWith("-L"))) ? 
				args[argIndex++].substring(2) : null;
				
		// TODO parse the program
		Program prog = null;
				
		// Check if the interpreter module is requested
		if (args.length > argIndex) {
			
			// Extract the interpreter data
			String interperterMethod = null;
			String[] params = null;
			
			interperterMethod = args[argIndex++];
			
			params = new String[args.length - argIndex - 1];
			
			for (int i = 0; i < params.length; i++) {
				params[i] = args[argIndex++];
			}
			
			// Run the interpreter and print the result
			Object result = 
					new IceCoffeInterpreter(prog).executeMethod(interperterMethod, params);
			System.out.println(result);
		}
		else {

			// TODO print the symbol table
			
		}
		
				
		boolean isLibrary = false;
		
		try {
			

					
					
			// Parse all files
			for (String argStr : args) {
				
				// Check if from here there are library files
				if (argStr.equals("-L")) {
					isLibrary = true;
				}
				else {
					// Parse the file into an AST and print it
					Program programAST = parseFile(argStr, isLibrary);
					
					System.out.println(new PrettyPrint().visit(programAST));
				}
			}
			
		} catch (IceCoffeException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Error reading file...");
		}
	}
	
	private static Program parseFile(String file, boolean isLibrary) throws IceCoffeException, IOException {
		
		// Open the file and extract tokens
		FileInputStream fileStream = null;
		List<Token> tokens = null;
		
		try {
			fileStream = new FileInputStream(file);
			tokens = new Lexer(fileStream).getAllTokens();
			fileStream.close();
		} finally {
			if (fileStream != null)
				fileStream.close();
		}
		
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
