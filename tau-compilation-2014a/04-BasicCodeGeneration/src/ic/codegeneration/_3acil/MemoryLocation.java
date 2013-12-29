/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration._3acil;

public class MemoryLocation implements Operand {
	private Operand address;

	public MemoryLocation(Operand address) {
		this.address = address;
	}
	
	public Operand getAddress() {
		return address;
	}

	@Override
	public String getOperandString() {
		return (this.address.getOperandString());
	}
}
