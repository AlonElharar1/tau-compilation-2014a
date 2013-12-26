/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration._3acil;

public class MemoryLocation implements Operand {
	private int location;

	public MemoryLocation(int location) {
		this.location = location;
	}
	
	public int getLocation() {
		return location;
	}

	@Override
	public String getOperandString() {
		return (String.format("%d", this.location));
	}
	
}
