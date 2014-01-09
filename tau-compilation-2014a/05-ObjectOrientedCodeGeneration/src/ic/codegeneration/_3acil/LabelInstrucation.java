/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration._3acil;

public class LabelInstrucation extends Instrucation {
	private Label label;

	public LabelInstrucation(Label label) {
		this.label = label;
	}

	public Label getLabel() {
		return label;
	}

	@Override
	public String generateString() {
		return (String.format(":%s", this.label));
	}
}
