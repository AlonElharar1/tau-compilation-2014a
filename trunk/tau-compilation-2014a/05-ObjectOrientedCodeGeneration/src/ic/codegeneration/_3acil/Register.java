/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.codegeneration._3acil;

public class Register implements Operand, Comparable<Register> {

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

	@Override
	public int hashCode() {
		return (Integer.valueOf(registerNum).hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (!this.getClass().equals(obj.getClass())))
			return (false);
		
		return (this.registerNum == ((Register)obj).registerNum);
	}
	
	@Override
	public int compareTo(Register o) {
		return (Integer.valueOf(this.registerNum).compareTo(
				Integer.valueOf(o.registerNum)));
	}
}
