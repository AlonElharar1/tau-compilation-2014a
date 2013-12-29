/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.codegeneration._3acil;

public class Register implements Operand {

	private int registerNum;
	
	public Register(int registerNum) {
		this.registerNum = registerNum;
	}
	
	public int getRegisterNum() {
		return (this.registerNum);
	}

	@Override
	public String getOperandString() {
		return (String.format("$%d", this.registerNum));
	}
}
