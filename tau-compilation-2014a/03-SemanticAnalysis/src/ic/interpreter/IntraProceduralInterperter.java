/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.interpreter;

import ic.ast.Node;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.PrimitiveType;
import ic.ast.decl.Program;
import ic.ast.decl.Type;
import ic.ast.stmt.LocalVariable;
import ic.ast.stmt.Statement;
import ic.ast.stmt.StmtAssignment;

import java.util.HashMap;
import java.util.Stack;

public class IntraProceduralInterperter extends IceCoffeInterpreter {
	
	private HashMap<Node, Object> data = new HashMap<Node, Object>();
	private Object returnValue;
	
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
			
			// Parse parameter and add it to the data
			this.data.put(method.getFormals().get(i), 
					PrimitiveType.parse(
							((PrimitiveType)type).getDataType(), 
							parameters[i]));
		}
		
		return (method.accept(this));
	}
	
	@Override
	public Object visit(DeclStaticMethod method) {
		
		for (Statement stmt : method.getStatements()) {
			stmt.accept(this);
		}
		
		return (this.returnValue);
	}
	
	@Override
	public Object visit(LocalVariable localVariable) {

		this.data.put(localVariable, 
				localVariable.getInitialValue().accept(this));
		
		return (this.data.get(localVariable));
	}
	
	@Override
	public Object visit(StmtAssignment assignment) {
		
		Node location = assignment.getScope().findRef(assignment.getVariable()); 
		this.data.put(location, assignment.getAssignment().accept(this));
		
		return (this.data.get(location));
	}
}
