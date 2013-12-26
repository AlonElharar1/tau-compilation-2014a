/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration._3acil;

public class Immediate extends Operand {

	int value;

	public Immediate(int value) {
		this.value = value;
	}

	public int getValue() {
		return (this.value);
	}
	
	@Override
	public String toString() {
		return (Integer.toString(this.value));
	}
}
