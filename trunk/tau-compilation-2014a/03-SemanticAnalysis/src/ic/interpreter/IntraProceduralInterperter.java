/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.interpreter;

import ic.ast.decl.DeclMethod;
import ic.ast.decl.PrimitiveType;
import ic.ast.decl.Program;
import ic.ast.decl.Type;

import java.util.Stack;

public class IntraProceduralInterperter extends IceCoffeInterpreter {
	
	private Stack<Object> stack =  new Stack<Object>();
	
	public IntraProceduralInterperter(Program program) {
		super(program);
	}
	
	public Object executeMethod(String methodId, String[] parameters) {
		
		// Get the method
		DeclMethod method = this.program.getScope().findMethod(methodId);
		
		// Push parameters into the stack
		if (parameters.length != method.getFormals().size())
			throw new InterpreterRunTimeException(method.getLine(), 
					"invalid method parameters");
		
		for (int i = 0; i < parameters.length; i++) {
			Type type = method.getFormals().get(i).getType();
			
			if (!(type instanceof PrimitiveType))
				throw new InterpreterRunTimeException(type.getLine(), 
						"type not supported by the interpreter");
			
			// parse string and push it
			this.stack.push(PrimitiveType.parse(
					((PrimitiveType)type).getDataType(), 
					parameters[i]));
		}
		
		return (method.accept(this));
	}
}
