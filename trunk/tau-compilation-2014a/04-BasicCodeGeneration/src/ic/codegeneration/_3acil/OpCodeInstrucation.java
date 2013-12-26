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
		String str = this.getOpcode() + " ";
		
		for (Operand operand : this.getOperands()) {
			str += operand + " ";
		}
		
		return (str.trim());
	}
}
