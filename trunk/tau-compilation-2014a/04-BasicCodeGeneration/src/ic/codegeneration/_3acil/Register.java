/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.codegeneration._3acil;

public class Register extends Operand {

	private int registerNum;
	
	public Register(int registerNum) {
		super();
		this.registerNum = registerNum;
	}
	
	public int getRegisterNum() {
		return (this.registerNum);
	}

	@Override
	public String toString() {
		return (String.format("$%d", this.registerNum));
	}
}
