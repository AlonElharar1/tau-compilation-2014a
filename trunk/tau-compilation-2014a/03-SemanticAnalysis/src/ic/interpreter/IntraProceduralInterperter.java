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
import ic.ast.stmt.StmtBlock;
import ic.ast.stmt.StmtBreak;
import ic.ast.stmt.StmtContinue;
import ic.ast.stmt.StmtIf;
import ic.ast.stmt.StmtReturn;
import ic.ast.stmt.StmtWhile;

import java.util.HashMap;

public class IntraProceduralInterperter extends IceCoffeInterpreter {
	
	private HashMap<Node, Object> data = new HashMap<Node, Object>();
	
	@SuppressWarnings("serial")
	private class BreakException extends Error {};
	
	@SuppressWarnings("serial")
	private class ContinueException extends Error {};
	
	@SuppressWarnings("serial")
	private class ReturnException extends Error { 
		public Object value;

		public ReturnException(Object value) {
			this.value = value;
		} 
	};
	
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
		try {
			for (Statement stmt : method.getStatements()) {
				stmt.accept(this);
			}
		}
		catch (ReturnException ret) {
			return (ret.value);
		}
		
		return (null);
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
	
	@Override
	public Object visit(StmtBlock statementsBlock) {
		
		for (Statement stmt : statementsBlock.getStatements()) {
			stmt.accept(this);
		}
		
		return (null);
	}
	
	@Override
	public Object visit(StmtBreak breakStatement) {
		throw new BreakException();
	}
	
	@Override
	public Object visit(StmtContinue continueStatement) {
		throw new ContinueException();
	}
	
	@Override
	public Object visit(StmtIf ifStatement) {
		
		if ((Boolean)ifStatement.getCondition().accept(this)) {
			ifStatement.getOperation().accept(this);
		}
		else {
			ifStatement.getOperation().accept(this);
		}
			
		
		return (null);
	}
	
	@Override
	public Object visit(StmtReturn returnStatement) {
		throw new ReturnException(returnStatement.getValue().accept(this));
	}
	
	@Override
	public Object visit(StmtWhile whileStatement) {
		
		while ((Boolean)whileStatement.getCondition().accept(this)) {
			try {
				whileStatement.getOperation().accept(this);
			}
			catch (ContinueException e) {
				continue;
			}
			catch (BreakException e) {
				break;
			}
		}
		
		return (null);
	}
}
