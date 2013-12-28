/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration._3acil;

public class Label implements Operand {
	private String label;

	public Label(String label) {
		this.label = label;
	}
	
	public Label(int label) {
		this.label = Integer.toString(label);
	}
	
	public String getLabel() {
		return (this.label);
	}

	@Override
	public int hashCode() {
		return (this.label.hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (!this.getClass().equals(obj.getClass())))
			return (false);
		
		return (this.label.equals(((Label)obj).label));
	}
	
	@Override
	public String getOperandString() {
		return (String.format(":%s", this.label));
	}
	
	@Override
	public String toString() {
		return (this.label);
	}
}
