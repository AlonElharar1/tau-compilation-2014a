package ic.ast.decl;

import ic.ast.Visitor;

/**
 * Primitive data type AST node.
 * 
 */
public class PrimitiveType extends Type {

	private DataType type;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new primitive data type node.
	 * 
	 * @param line
	 *            Line number of type declaration.
	 * @param type
	 *            Specific primitive data type.
	 */
	public PrimitiveType(int line, DataType type) {
		super(line);
		this.type = type;
	}

	public String getDisplayName() {
		return type.toString();
	}
	
	public DataType getDataType() {
		return (this.type);
	}

	public static Object parse(DataType type, String objStr) {
		
		switch (type) {
			case BOOLEAN: {
				return (Boolean.parseBoolean(objStr));
			}
			case INT: {
				return (Integer.parseInt(objStr));
			}
			case STRING: {
				return (objStr);
			}
			default:
				break;
		}
		
		return (null);
	}
	
	/**
	 * Enumerated type listing the primitive data types.
	 */
	public enum DataType {

		INT("int", Integer.class), 
		BOOLEAN("boolean", Boolean.class), 
		STRING("string", String.class), 
		VOID("void", Void.class);
		
		private String description;
		private Class<?> javaType;

		private DataType(String description, Class<?> javaType) {
			this.description = description;
			this.javaType = javaType;
		}

		@Override
		public String toString()
		{
			return description;
		}
		
		public Class<?> getJavaType() {
			return (this.javaType);
		}
		
		public static DataType find(String description) {
			for (DataType dataType : DataType.values()) {
				if (dataType.description.equals(description)) {
					return (dataType);
				}
			}
			
			throw new Error("internal error; primitive type not found: " + description);
		}
	}

}
