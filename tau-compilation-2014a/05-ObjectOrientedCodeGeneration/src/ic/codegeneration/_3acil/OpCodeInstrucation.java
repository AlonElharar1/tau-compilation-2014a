/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration._3acil;

public class OpCodeInstrucation extends Instrucation {
	
	private OpCodes opcode;
	private Operand[] operands;
	
	public OpCodeInstrucation(OpCodes opcode, Operand[] operands) {
		this.opcode = opcode;
		this.operands = operands.clone();
	}

	public OpCodes getOpcode() {
		return (this.opcode);
	}

	public Operand[] getOperands() {
		return (this.operands);
	}

	@Override
	public String generateString() {
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append("\t");
		strBuilder.append(this.getOpcode().getOpcodeString());
		strBuilder.append(" ");
		
		for (Operand operand : this.getOperands()) {
			strBuilder.append(operand.getOperandString());
			strBuilder.append(" ");
		}
		
		strBuilder.deleteCharAt(strBuilder.length() - 1);
		
		return (strBuilder.toString());
	}
}
