package ic.ast.expr;

import ic.ast.Visitor;
import ic.ast.decl.Type;
import ic.ast.decl.*;

/**
 * Array reference AST node.
 * 
 */
public class RefArrayElement extends Ref {

	private Expression array;

	private Expression index;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new array reference node.
	 * 
	 * @param array
	 *            Expression representing an array.
	 * @param index
	 *            Expression representing a numeric index.
	 */
	public RefArrayElement(Expression array, Expression index) {
		super(array.getLine());
		this.array = array;
		this.index = index;
	}

	public Expression getArray() {
		return array;
	}

	public Expression getIndex() {
		return index;
	}

	@Override
	public Type getExpresstionType() {
		
		Type arrayType = this.getArray().getExpresstionType();
		Type cellType = null;
		
		if (arrayType instanceof ClassType) {
			cellType = new ClassType(this.getLine(), 
					((ClassType)arrayType).getClassName());
		}
		else if (arrayType instanceof PrimitiveType) {
			cellType = new PrimitiveType(this.getLine(), 
					((PrimitiveType)arrayType).getDataType());
		}
		
		cellType.setArrayDimension(cellType.getArrayDimension() - 1);
		
		return (cellType);
	}
	
}
