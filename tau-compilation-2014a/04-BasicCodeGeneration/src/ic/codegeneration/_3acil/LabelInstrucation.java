/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration._3acil;

public class LabelInstrucation extends Instrucation {
	private String identifer;

	public LabelInstrucation(String identifer) {
		this.identifer = identifer;
	}

	public LabelInstrucation(int identifer) {
		this(Integer.toString(identifer));
	}

	public String getIdentifer() {
		return identifer;
	}

	@Override
	public String generateString() {
		return (String.format(":%s", this.identifer));
	}
}
