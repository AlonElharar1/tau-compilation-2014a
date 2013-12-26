/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration._3acil;

public class Label extends Operand {
	private String label;

	public Label(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return (this.label);
	}

	@Override
	public String toString() {
		return (String.format(":%s", this.label));
	}
}
