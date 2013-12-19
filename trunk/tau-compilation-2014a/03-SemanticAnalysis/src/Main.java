/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import ic.IceCoffeException;
import ic.ast.decl.Program;
import ic.interpreter.IntraProceduralInterperter;
import ic.semantics.checks.SemanticChecker;
import ic.semantics.scopes.ScopesBuilder;
import ic.syntax.IceCoffeParser;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		// Validate arguments
		if (args.length < 1) {
			System.out
					.println("Usage: program [-Llib] [method] [parameter1] ... ");
			return;
		}

		int argIndex = 0;

		// Extract the program file name
		String programFile = args[argIndex++];

		// Extract library file if given
		String libraryFile = ((args.length > 1) && (args[1].startsWith("-L"))) ? args[argIndex++]
				.substring(2) : null;

		try {

			// Parse the program
			Program prog = new IceCoffeParser(programFile, libraryFile).parse();

			// Build the symbol tables
			prog.accept(new ScopesBuilder());

			// Do Semantics checks
			new SemanticChecker().runAllChecks(prog);

			// Check if the interpreter module is requested
			if (args.length <= argIndex) {
				
				// Print the symbol table
				prog.getScope().print();

			} else {
				
				// Extract the interpreter data
				String interperterMethod = args[argIndex++];
				String[] params = new String[args.length - argIndex - 1];

				for (int i = 0; i < params.length; i++) {
					params[i] = args[argIndex++];
				}

				// Run the interpreter and print the result
				Object result = new IntraProceduralInterperter(prog).executeMethod(
						interperterMethod, params);
				System.out.println(result);
			}

		} catch (IceCoffeException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Error reading file...");
		}
	}
}
