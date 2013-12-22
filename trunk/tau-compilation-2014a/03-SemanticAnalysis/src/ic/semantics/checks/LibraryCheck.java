/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.checks;

import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclLibraryMethod;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.Program;
import ic.semantics.SemanticException;

public class LibraryCheck extends SemanticCheck {

	@Override
	public Object visit(Program program) {
		
		for (DeclClass icClass : program.getClasses())
			if (!icClass.getName().equals("Library"))
				icClass.accept(this);
	
		return (null);
	}

	@Override
	public Object visit(DeclClass icClass) {
		
		for (DeclMethod method : icClass.getMethods())
			method.accept(this);
		
		return (null);
	}

	@Override
	public Object visit(DeclLibraryMethod method) {
		throw new SemanticException(method.getLine(),
				"library methods can only be defined in the library class, which is named 'Library'");
	}

	@Override
	public void runCheck(Program program) {
		program.accept(this);
	}
}
