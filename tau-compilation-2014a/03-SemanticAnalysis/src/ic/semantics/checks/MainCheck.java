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
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.DeclVirtualMethod;
import ic.ast.decl.Program;
import ic.semantics.SemanticException;

public class MainCheck extends SemanticCheck {

	private boolean hasMainMethod = false;
	
	@Override
	public Object visit(Program program) {
		
		for (DeclClass icClass : program.getClasses())
				icClass.accept(this);
	
		return (null);
	}

	@Override
	public Object visit(DeclClass icClass) {
		
		for (DeclMethod method : icClass.getMethods())
			if (method.getName().equals("main"))
				method.accept(this);
		
		return (null);
	}

	@Override
	public Object visit(DeclVirtualMethod method) {
		throw new SemanticException(method.getLine(), 
				"the main method must be static");
	}

	@Override
	public Object visit(DeclStaticMethod method) {
		
		if (hasMainMethod) 
			throw new SemanticException(method.getLine(),
					"there is more then one 'main' method");
		
		if ((method.getFormals().size() == 1) && 
		    (method.getFormals().get(0).getType().getDisplayName().equals("string")) &&
		    (method.getFormals().get(0).getType().getArrayDimension() == 1)) {
			hasMainMethod = true;
		}
		else {
			throw new SemanticException(method.getLine(),
					"the 'main' method has wrong signature");
		}
		
		return (null);
	}

	@Override
	public Object visit(DeclLibraryMethod method) {
		throw new SemanticException(method.getLine(),
				"the main method can not be library method");
	}

	@Override
	public void runCheck(Program program) {
		
		program.accept(this);
		
		if (!hasMainMethod) 
			throw new SemanticException(0 ,
					"There is no 'main' method in the program");

	}
}
