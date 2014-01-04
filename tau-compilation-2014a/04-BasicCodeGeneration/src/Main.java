/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import ic.IceCoffeException;
import ic.ast.decl.Program;
import ic.codegeneration.ASTTranslator;
import ic.codegeneration._3acil._3ACILGenerator;
import ic.codegeneration._3acil.optimizers.ReuseRegistersOptimizer;
import ic.semantics.checks.SemanticChecker;
import ic.semantics.scopes.ScopesBuilder;
import ic.syntax.IceCoffeParser;

import java.io.IOException;

public class Main {

	/**
	 * @bonus constants, reusing
	 */
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
		String libraryFile = ((args.length > 1) && (args[1].startsWith("-L"))) ? args[argIndex++]
				.substring(2) : null;

		try {

			// Parse the program
			Program prog = new IceCoffeParser(programFile, libraryFile).parse();

			// Build the symbol tables
			prog.accept(new ScopesBuilder());

			// Do Semantics checks
			new SemanticChecker().runAllChecks(prog);

			// Generate 3ACIL code
			_3ACILGenerator generator = new _3ACILGenerator();
			ASTTranslator translator = new ASTTranslator(generator);
			translator.translate(prog);
			
			// Optimize
			new ReuseRegistersOptimizer().optimize(generator);
			
			// Out final code to screen
			translator.write(System.out);
			
		} catch (IceCoffeException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("IO Error: " + e.getLocalizedMessage());
		}
	}
}
