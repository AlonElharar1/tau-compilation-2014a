package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.ClassType;
import ic.ast.decl.PrimitiveType;
import ic.ast.decl.PrimitiveType.DataType;
import ic.ast.decl.Type;
import ic.semantics.SemanticException;

/**
 * Abstract base class for binary operation AST nodes.
 * 
 */
public class BinaryOp extends Expression {

	private Expression operand1;

	private BinaryOps operator;

	private Expression operand2;

	/**
	 * Constructs a new binary operation node.
	 * 
	 * @param operand1
	 *            The first operand.
	 * @param operator
	 *            The operator.
	 * @param operand2
	 *            The second operand.
	 */
	public BinaryOp(int line, Expression operand1, BinaryOps operator,
			Expression operand2) {
		super(line);
		this.operand1 = operand1;
		this.operator = operator;
		this.operand2 = operand2;
	}

	public BinaryOps getOperator() {
		return operator;
	}

	public Expression getFirstOperand() {
		return operand1;
	}

	public Expression getSecondOperand() {
		return operand2;
	}

	@Override
	public Object accept(Visitor visitor)
	{
		return visitor.visit(this);
	}

	/**
	 * Enumerated type listing all possible binary operators.
	 */
	public enum BinaryOps {

		PLUS("+", "add"),
		MINUS("-", "subtract"),
		MULTIPLY("*", "multiply"),
		DIVIDE("/", "divide"),
		MOD("%", "modulo"),
		LAND("&&", "logical and"),
		LOR("||", "logical or"),
		LT("<", "less than"),
		LTE("<=", "less than or equal to"),
		GT(">", "greater than"),
		GTE(">=", "greater than or equal to"),
		EQUAL("==", "equals"),
		NEQUAL("!=", "not equals");
		
		private String operator, description;

		private BinaryOps(String operator, String description) {
			this.operator = operator;
			this.description = description;
		}

		public String toString()       { return operator; }
		public String getDescription() { return description; }
		
		public static BinaryOps find(String op)
		{
			for (BinaryOp.BinaryOps x : BinaryOp.BinaryOps.values()) {
				if (x.toString().equals(op)) return x;
			}
			
			throw new Error("internal error; binary operator not found: " + op);
		}
	}

	@Override
	public Type getExpresstionType() {
		
		Type firstType = this.getFirstOperand().getExpresstionType();
		Type secondType = this.getSecondOperand().getExpresstionType();
		
		// Class types can only used with '==' or '!='
		if ((firstType instanceof ClassType) &&
		    (secondType instanceof ClassType))  {
			
			ClassType firstClass = (ClassType)firstType;
			ClassType secondClass = (ClassType)secondType;
			
			if ((firstClass.getClassName() == secondClass.getClassName()) &&
				((this.getOperator() == BinaryOps.EQUAL) ||
				 (this.getOperator() == BinaryOps.NEQUAL))) {
				return (new PrimitiveType(this.getLine(),
							PrimitiveType.DataType.BOOLEAN));
			}
		}
		// Check primitive types
		else if ((firstType instanceof PrimitiveType) &&
			     (secondType instanceof PrimitiveType))  {
			
			PrimitiveType firstPrimitive = (PrimitiveType)firstType;
			PrimitiveType secondPrimitive = (PrimitiveType)secondType;
			
			// Only same types!
			if (firstPrimitive.getDataType() == secondPrimitive.getDataType()) {
				
				switch (this.getOperator()) {
				case PLUS:
					
					if ((firstPrimitive.getDataType() == DataType.STRING) ||
						(firstPrimitive.getDataType() == DataType.INT)) {
						return (firstPrimitive);
					}
					
					break;
					
				case MINUS:
				case MULTIPLY:
				case DIVIDE:
				case MOD:
					
					if (firstPrimitive.getDataType() == DataType.INT) {
						return (firstPrimitive);
					}
					
				case GT:
				case GTE:
				case LT:
				case LTE:
					
					if (firstPrimitive.getDataType() == DataType.INT) {
						return (new PrimitiveType(this.getLine(),
									PrimitiveType.DataType.BOOLEAN));
					}
					
					break;
					
				case LAND:
				case LOR:
					
					if (firstPrimitive.getDataType() == DataType.BOOLEAN) {
						return (firstPrimitive);
					}
					
					break;
					
				case EQUAL:
				case NEQUAL:
					
					return (new PrimitiveType(this.getLine(),
								PrimitiveType.DataType.BOOLEAN));
					
				default:
					break;
				}
			}
		}
		
		throw new SemanticException(this.getLine(), 
				"operator '" + this.getOperator() + "' doesnt support these types");
	}
}
