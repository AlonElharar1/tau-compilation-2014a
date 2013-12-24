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
import ic.ast.expr.BinaryOp;
import ic.ast.expr.Expression;
import ic.ast.expr.Length;
import ic.ast.expr.Literal;
import ic.ast.expr.NewArray;
import ic.ast.expr.RefArrayElement;
import ic.ast.expr.RefVariable;
import ic.ast.expr.UnaryOp;
import ic.ast.stmt.LocalVariable;
import ic.ast.stmt.Statement;
import ic.ast.stmt.StmtAssignment;
import ic.ast.stmt.StmtBlock;
import ic.ast.stmt.StmtBreak;
import ic.ast.stmt.StmtContinue;
import ic.ast.stmt.StmtIf;
import ic.ast.stmt.StmtReturn;
import ic.ast.stmt.StmtWhile;

import java.lang.reflect.Array;
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
		return (this.executeMethod(
				methodId.substring(0, methodId.indexOf('.')),
				methodId.substring(methodId.indexOf('.') + 1),
				parameters));
	}
	
	public Object executeMethod(String className, String methodName, String[] parameters) {
		
		// Get the method
		DeclMethod method = this.program.getScope().findMethod(className, methodName);
		
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
				localVariable.getInitialValue() != null ?
				localVariable.getInitialValue().accept(this) : null);
		
		return (this.data.get(localVariable));
	}
	
	@Override
	public Object visit(StmtAssignment assignment) {
		
		Object value = assignment.getAssignment().accept(this);
		
		if (assignment.getVariable() instanceof RefVariable) {
			Node location = assignment.getScope().findLocalVariable(
					((RefVariable)assignment.getVariable()).getName());
			this.data.put(location, value);
			
		}
		else if (assignment.getVariable() instanceof RefArrayElement) {
		
			Expression arrayRef = ((RefArrayElement)assignment.getVariable()).getArray();
			
			if (arrayRef instanceof RefVariable) {
				Node location = assignment.getScope().findLocalVariable(
						((RefVariable)arrayRef).getName());
			
				Integer index = (Integer)((RefArrayElement)assignment.getVariable()).getIndex().accept(this);
				
				Array.set(this.data.get(location), index , value);
			}
		}
		else {
			throw new InterpreterRunTimeException(assignment.getLine(), 
					"the assignment is not supported by the interpreter");
		}
		
		return (null);
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

	@Override
	public Object visit(BinaryOp binaryOp) {
		
		switch (binaryOp.getOperator()) {
		case PLUS:
			Object firstOperandVal = binaryOp.getFirstOperand().accept(this);
			Object secondOperandVal = binaryOp.getSecondOperand().accept(this);
			
			if (firstOperandVal instanceof String)
				return ((String)firstOperandVal + secondOperandVal);
			else if (secondOperandVal instanceof String)
				return (firstOperandVal + (String)secondOperandVal);
			else if (firstOperandVal instanceof Integer)
				return ((Integer)firstOperandVal + (Integer)secondOperandVal);
			
			break;
			
		case MINUS:
			return ((Integer)binaryOp.getFirstOperand().accept(this) -
					(Integer)binaryOp.getSecondOperand().accept(this));
		case MULTIPLY:
			return ((Integer)binaryOp.getFirstOperand().accept(this) *
					(Integer)binaryOp.getSecondOperand().accept(this));
		case DIVIDE:
			return ((Integer)binaryOp.getFirstOperand().accept(this) /
					(Integer)binaryOp.getSecondOperand().accept(this));
		case MOD:
			return ((Integer)binaryOp.getFirstOperand().accept(this) %
					(Integer)binaryOp.getSecondOperand().accept(this));
		case EQUAL:
			return (binaryOp.getFirstOperand().accept(this).equals(
					binaryOp.getSecondOperand().accept(this)));
		case NEQUAL:
			return (!binaryOp.getFirstOperand().accept(this).equals(
					 binaryOp.getSecondOperand().accept(this)));
		case GT:
			return ((Integer)binaryOp.getFirstOperand().accept(this) >
					(Integer)binaryOp.getSecondOperand().accept(this));
		case GTE:
			return ((Integer)binaryOp.getFirstOperand().accept(this) >=
					(Integer)binaryOp.getSecondOperand().accept(this));
		case LT:
			return ((Integer)binaryOp.getFirstOperand().accept(this) <
					(Integer)binaryOp.getSecondOperand().accept(this));
		case LTE:
			return ((Integer)binaryOp.getFirstOperand().accept(this) <=
					(Integer)binaryOp.getSecondOperand().accept(this));
		case LAND:
			return ((Boolean)binaryOp.getFirstOperand().accept(this) &&
					(Boolean)binaryOp.getSecondOperand().accept(this));
		case LOR:
			return ((Boolean)binaryOp.getFirstOperand().accept(this) &&
					(Boolean)binaryOp.getSecondOperand().accept(this));
		default:
			break;
		}
		
		return (null);
	}

	@Override
	public Object visit(Length length) {
		return (Array.getLength(length.getArray().accept(this)));
	}

	@Override
	public Object visit(Literal literal) {
		return (literal.getValue());
	}

	@Override
	public Object visit(NewArray newArray) {
		if (!(newArray.getType() instanceof PrimitiveType))
			throw new InterpreterRunTimeException(newArray.getLine(), 
					"allocation of non primitive types is not supported");
		
		Class<?> type = ((PrimitiveType)newArray.getType()).getDataType().getJavaType();
		Integer size = (Integer)newArray.getSize().accept(this);
		
		return (Array.newInstance(type, size));
	}

	@Override
	public Object visit(RefArrayElement location) {
		
		if (location.getArray() instanceof RefVariable) {
			Node arrayVar = location.getScope().findLocalVariable(
					((RefVariable)location.getArray()).getName());
		
			return (Array.get(this.data.get(arrayVar), (Integer)location.getIndex().accept(this)));
		}
		
		super.visit(location);
		
		return null;
	}

	@Override
	public Object visit(RefVariable location) {
		return (this.data.get(location.getScope().findLocalVariable(location.getName())));
	}

	@Override
	public Object visit(UnaryOp unaryOp) {
		
		switch (unaryOp.getOperator()) {
		case LNEG:
			return (!(Boolean)unaryOp.getOperand().accept(this));
		case UMINUS:
			return (-(Integer)unaryOp.getOperand().accept(this));
		default:
			break;
		}
		
		return (null);
	}
}
