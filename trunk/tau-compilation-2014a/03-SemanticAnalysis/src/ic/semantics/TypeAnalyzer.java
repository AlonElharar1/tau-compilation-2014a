/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.semantics;

import ic.ast.EmptyVisitor;
import ic.ast.Node;
import ic.ast.decl.ClassType;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.Parameter;
import ic.ast.decl.PrimitiveType;
import ic.ast.decl.PrimitiveType.DataType;
import ic.ast.decl.Type;
import ic.ast.expr.BinaryOp;
import ic.ast.expr.BinaryOp.BinaryOps;
import ic.ast.expr.Expression;
import ic.ast.expr.Length;
import ic.ast.expr.Literal;
import ic.ast.expr.NewArray;
import ic.ast.expr.NewInstance;
import ic.ast.expr.RefArrayElement;
import ic.ast.expr.RefField;
import ic.ast.expr.RefVariable;
import ic.ast.expr.StaticCall;
import ic.ast.expr.This;
import ic.ast.expr.UnaryOp;
import ic.ast.expr.UnaryOp.UnaryOps;
import ic.ast.expr.VirtualCall;
import ic.ast.stmt.LocalVariable;
import ic.ast.stmt.StmtAssignment;
import ic.ast.stmt.StmtCall;
import ic.semantics.scopes.IceCoffeScope;

public class TypeAnalyzer extends EmptyVisitor {

	public boolean isInstanceOf(IceCoffeScope scope, Type subType, Type type) {

		if (subType.getDisplayName().equals(type.getDisplayName()))
			return (true);
		else if ((subType instanceof ClassType) && (type instanceof ClassType)) {
			DeclClass superClass = 
					scope.findClass(scope.findClass(subType.getDisplayName()).getSuperClassName());
			
			while (superClass != null) {
				
				if (superClass.getName().equals(type.getDisplayName()))
					return (true);
				
				superClass = scope.findClass(superClass.getSuperClassName());
			}
		}
		return (false);
	}
	
	public Type getExpressionType(Expression expr) {
		return ((Type)expr.accept(this));
	}
	
	@Override
	public Object visit(Parameter formal) {
		return (formal.getType());
	}

	@Override
	public Object visit(PrimitiveType type) {
		return (type);
	}

	@Override
	public Object visit(ClassType type) {
		return (type);
	}

	@Override
	public Object visit(StmtAssignment assignment) {
		return (assignment.getVariable().accept(this));
	}

	@Override
	public Object visit(StmtCall callStatement) {
		return (callStatement.getCall().accept(this));
	}

	@Override
	public Object visit(RefVariable location) {
		Node varDeclNode = location.getScope().findLocalVariable(location.getName());
		
		if (varDeclNode instanceof LocalVariable) 
			return (((LocalVariable)varDeclNode).getType());
		else if (varDeclNode instanceof Parameter)
			return (((Parameter)varDeclNode).getType());
		else if (varDeclNode instanceof DeclField)
			return (((DeclField)varDeclNode).getType());
		
		return (null);
	}

	@Override
	public Object visit(RefField location) {
		String objectName = 
				this.getExpressionType(location.getObject()).getDisplayName();
		return (location.getScope().findField(objectName, location.getField()).getType());
	}

	@Override
	public Object visit(RefArrayElement location) {
		Type arrayType = this.getExpressionType(location.getArray());
		Type cellType = null;
		
		if (arrayType instanceof ClassType) {
			cellType = new ClassType(location.getLine(), 
					((ClassType)arrayType).getClassName());
		}
		else if (arrayType instanceof PrimitiveType) {
			cellType = new PrimitiveType(location.getLine(), 
					((PrimitiveType)arrayType).getDataType());
		}
		
		cellType.setArrayDimension(cellType.getArrayDimension() - 1);
		
		return (cellType);
	}

	@Override
	public Object visit(StaticCall call) {
		return (call.getScope().findMethod(call.getClassName(), call.getMethod()).getType());
	}

	@Override
	public Object visit(VirtualCall call) {
		
		String className = (call.getObject() == null) ? 
				call.getScope().currentClass().getName() :
				this.getExpressionType(call.getObject()).getDisplayName();
		
		DeclMethod method = 
				call.getScope().findMethod(className, call.getMethod());
		
		if (method != null)
			return (method.getType());
		
		throw new SemanticException(call.getLine(), 
				"method doesn't exists");
	}

	@Override
	public Object visit(This thisExpression) {
		return (new ClassType(thisExpression.getLine(), 
			thisExpression.getScope().currentClass().getName()));
	}

	@Override
	public Object visit(NewInstance newClass) {
		return (new ClassType(newClass.getLine(), newClass.getName()));
	}

	@Override
	public Object visit(NewArray newArray) {
		return (newArray.getType());
	}

	@Override
	public Object visit(Length length) {
		return (new PrimitiveType(length.getLine(), 
				PrimitiveType.DataType.INT));
	}

	@Override
	public Object visit(Literal literal) {
		return (literal.getType());
	}

	@Override
	public Object visit(UnaryOp unaryOp) {
		
		Type operandType = this.getExpressionType(unaryOp.getOperand());
		
		if (operandType instanceof PrimitiveType) {
			PrimitiveType operandPrimitive = (PrimitiveType)operandType;
			
			if (((operandPrimitive.getDataType() == DataType.BOOLEAN) &&
				 (unaryOp.getOperator() == UnaryOps.LNEG)) ||
				((operandPrimitive.getDataType() == DataType.INT) &&
				 (unaryOp.getOperator() == UnaryOps.UMINUS))) {
				return (operandPrimitive);
			}
		}
		
		throw new SemanticException(unaryOp.getLine(), 
			"operator '" + unaryOp.getOperator() + "' doesnt support this type");
	}

	@Override
	public Object visit(BinaryOp binaryOp) {
		Type firstType = this.getExpressionType(binaryOp.getFirstOperand());
		Type secondType = this.getExpressionType(binaryOp.getSecondOperand());
		
		if (((firstType instanceof ClassType) &&
			 (binaryOp.getSecondOperand() instanceof Literal) &&
			 (secondType.getDisplayName().equals(PrimitiveType.DataType.VOID.toString()))) ||
			((secondType instanceof ClassType) &&
			 (binaryOp.getFirstOperand() instanceof Literal) &&
			 (firstType.getDisplayName().equals(PrimitiveType.DataType.VOID.toString())))) {
			
			if ((binaryOp.getOperator() == BinaryOps.EQUAL) ||
				(binaryOp.getOperator() == BinaryOps.NEQUAL))
				return (new PrimitiveType(binaryOp.getLine(),
						PrimitiveType.DataType.BOOLEAN));
		}
		else if ((firstType instanceof ClassType) &&
				 (secondType instanceof ClassType))  {
			
			ClassType firstClass = (ClassType)firstType;
			ClassType secondClass = (ClassType)secondType;
			
			if ((this.isInstanceOf(binaryOp.getScope(), firstClass, secondClass) ||
				(this.isInstanceOf(binaryOp.getScope(), secondClass, firstClass))) &&
				((binaryOp.getOperator() == BinaryOps.EQUAL) ||
				 (binaryOp.getOperator() == BinaryOps.NEQUAL))) {
				return (new PrimitiveType(binaryOp.getLine(),
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
				
				switch (binaryOp.getOperator()) {
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
						return (new PrimitiveType(binaryOp.getLine(),
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
					
					return (new PrimitiveType(binaryOp.getLine(),
								PrimitiveType.DataType.BOOLEAN));
					
				default:
					break;
				}
			}
		}
		
		throw new SemanticException(binaryOp.getLine(), 
				String.format("Invalid binary op (%s) on expressions",
						binaryOp.getOperator()));
	}

}
