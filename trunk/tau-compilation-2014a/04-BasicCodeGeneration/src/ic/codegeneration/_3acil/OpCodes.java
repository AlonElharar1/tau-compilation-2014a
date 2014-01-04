/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.codegeneration._3acil;

public enum OpCodes {

	// Move
	MOV("=", "Move", 2, new Integer[] { 0 }, new Integer[] { 1 }),
	
	// Arithmetic
	ADD("+", "Arithmetic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	SUB("-", "Arithmetic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	MUL("*", "Arithmetic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	DIV("/", "Arithmetic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	MOD("%", "Arithmetic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	NEG("-", "Arithmetic", 2, new Integer[] { 0 }, new Integer[] { 1 }),
	EQ("==", "Arithmetic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	NEQ("!=", "Arithmetic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	LT("<", "Arithmetic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	LTE("<=", "Arithmetic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	GT(">", "Arithmetic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	GTE(">=", "Arithmetic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),

	// Memory
	READ("[]", "Memory", 2, new Integer[] { 0 }, new Integer[] { 1 }),
	WRITE("[]=", "Memory", 2, new Integer[] { 0, 1 }, new Integer[] { }),
	
	// Logic
	AND("&&", "Logic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	OR("||", "Logic", 3, new Integer[] { 0, 1 }, new Integer[] { 2 }),
	NOT("!", "Logic", 2, new Integer[] { 0 }, new Integer[] { 1 }),
	
	// Control
	GOTO("goto", "Control", 1, new Integer[] { 0 }, new Integer[] { }),
	IF("if", "Control", 2, new Integer[] { 0, 1 }, new Integer[] { }),
	NIF("if!", "Control", 2, new Integer[] { 0, 1 }, new Integer[] { }),
	IFE("if=", "Control", 3, new Integer[] { 0, 1, 2 }, new Integer[] { }),
	IFNE("if!=", "Control", 3, new Integer[] { 0, 1, 2 }, new Integer[] { }),

	// Invocation
	PARAM("param", "Invocation", 1, new Integer[] { 0 }, new Integer[] { }),
	CALL("call", "Invocation", 1, new Integer[] { 0 }, new Integer[] { }),
	CALLINTO("call", "Invocation", 2, new Integer[] { 0 }, new Integer[] { 1 }),
	RET("ret", "Invocation", 0, new Integer[] { }, new Integer[] { }),
	RETVAL("ret", "Invocation", 1, new Integer[] { 0 }, new Integer[] { }),
	
	;

	private String opcodeStr;
	private String category;
	private int numberOfOperands;
	private Integer[] useOperators;
	private Integer[] defOperators;

	private OpCodes(String opcodeStr, String category, int numberOfOperands,
			Integer[] useOperators, Integer[] defOperators) {
		this.opcodeStr = opcodeStr;
		this.category = category;
		this.numberOfOperands = numberOfOperands;
		this.useOperators = useOperators;
		this.defOperators = defOperators;
	}
	
	public String getOpcodeString() {
		return (this.opcodeStr);
	}
	
	public String getCategory() {
		return category;
	}

	public int getNumberOfOperands() {
		return (this.numberOfOperands);
	}
	
	public Integer[] getUseOperators() {
		return useOperators;
	}

	public Integer[] getDefOperators() {
		return defOperators;
	}
}
