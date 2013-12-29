/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.codegeneration._3acil;

public enum OpCodes {

	// Move
	MOV("=", 2),
	
	// Arithmetic
	ADD("+", 3),
	SUB("-", 3),
	MUL("*", 3),
	DIV("/", 3),
	MOD("%", 3),
	NEG("-", 2),
	EQ("==", 3),
	NEQ("!=", 3),
	LT("<", 3),
	LTE("<=", 3),
	GT(">", 3),
	GTE(">=", 3),

	// Memory
	READ("[]", 2),
	WRITE("[]=", 2),
	
	// Logic
	AND("&&", 3),
	OR("||", 3),
	NOT("!", 2),
	
	// Control
	GOTO("goto", 1),
	IF("if", 2),
	NIF("if!", 2),
	IFE("if=", 3),
	IFNE("if!=", 3),

	// Invocation
	PARAM("param", 1),
	CALL("call", 1),
	CALLINTO("call", 2),
	RET("ret", 0),
	RETVAL("ret", 1),
	
	;

	private String opcodeStr;
	private int numberOfOperands;
	
	private OpCodes(String opcodeStr, int numberOfOperands) {
		this.opcodeStr = opcodeStr;
		this.numberOfOperands = numberOfOperands;
	}
	
	public String getOpcodeString() {
		return (this.opcodeStr);
	}
	
	public int getNumberOfOperands() {
		return (this.numberOfOperands);
	}
}
