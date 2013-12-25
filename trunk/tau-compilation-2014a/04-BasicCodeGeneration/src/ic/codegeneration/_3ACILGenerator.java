/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.codegeneration;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class _3ACILGenerator {

	private HashMap<String, Object> data = new HashMap<String, Object>();
	private List<Instrucation> instrucations = new ArrayList<Instrucation>();
	
	public _3ACILGenerator() {
	}
	
	/**
	 * Adds something to the data section
	 * @param data
	 */
	public void addData(String label, String data) {
		// TODO implement
	}
	
	/**
	 * Adds something to the data section
	 * @param data
	 */
	public void addData(String label, int data) {
		// TODO implement
	}
	
	/**
	 * Adds an label
	 * @param identifer
	 */
	public void addLabel(String identifer) {
		// TODO implement
	}
	
	/**
	 * Adds an label
	 * @param identifer
	 */
	public void addLabel(int identifer) {
		// TODO implement
	}
	
	/**
	 * Adds an instruction
	 * @param opcode
	 * @param operands
	 */
	public void addOpcode(OpCodes opcode, String... operands) {
		// TODO implement
	}
	
	/**
	 * Writes the generated code into a stream
	 * @param path
	 * @throws IOException
	 */
	public void write(PrintStream stream) throws IOException {
		// TODO implement
	}
	
	private abstract class Instrucation {
		public abstract String generateString();
	}
	
	private class LabelInstrucation extends Instrucation {
		private String identifer;

		public LabelInstrucation(String identifer) {
			this.identifer = identifer;
		}

		public LabelInstrucation(int identifer) {
			this(Integer.toString(identifer));
		}

		public String getIdentifer() {
			return identifer;
		}

		@Override
		public String generateString() {
			return (String.format(":%s", this.identifer));
		}
	}
	
	private class OpcodeInstrucation extends Instrucation {
		
		private OpCodes opcode;
		private String[] operands;
		
		public OpcodeInstrucation(OpCodes opcode, String[] operands) {
			this.opcode = opcode;
			this.operands = operands.clone();
		}

		public OpCodes getOpcode() {
			return (this.opcode);
		}

		public String[] getOperands() {
			return (this.operands);
		}

		@Override
		public String generateString() {
			String str = this.getOpcode() + " ";
			
			for (String operand : this.getOperands()) {
				str += operand + " ";
			}
			
			return (str.trim());
		}
	}
	
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
		ST("<", 3),
		STE("<=", 3),
		GT(">", 3),
		GTE(">=", 3),

		// Memory
		RED("[]", 2),
		WRT("[]=", 2),
		
		// Logic
		AND("&&", 3),
		OR("||", 3),
		NOT("!", 2),
		
		// Control
		GTO("goto", 1),
		IF("if", 2),
		NIF("if!", 2),
		IFE("if=", 3),
		IFNE("if!=", 3),

		// Invocation
		PRM("param", 1),
		CALL("call", 1),
		CALL2("call", 2),
		RET("ret", 1),
		RET2("ret", 2),
		
		;

		private String opcode;
		private int numberOfOperands;
		
		private OpCodes(String opcode, int numberOfOperands) {
			this.opcode = opcode;
			this.numberOfOperands = numberOfOperands;
		}
		
		public String getOpcode() {
			return (this.opcode);
		}
		
		public int getNumberOfOperands() {
			return (this.numberOfOperands);
		}
	}
}
