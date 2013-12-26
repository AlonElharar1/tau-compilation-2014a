/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.codegeneration._3acil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class _3ACILGenerator {

	private HashMap<Label, Object> data = new LinkedHashMap<Label, Object>();
	private List<Instrucation> instrucations = new ArrayList<Instrucation>();
	
	public _3ACILGenerator() {
	}
	
	/**
	 * Adds something to the data section
	 * @param data
	 */
	public Label addData(String label, String data) {
		// TODO implement
		return (null);
	}
	
	/**
	 * Adds something to the data section
	 * @param data
	 */
	public Label addData(String label, int data) {
		// TODO implement
		return (null);
	}
	
	/**
	 * Creates a new unique label
	 * @return
	 */
	public Label generateUniqueLabel() {
		// TODO implement
		return null;
	}
	
	/**
	 * Adds an label
	 * @param label
	 */
	public void addLabel(Label label) {
		// TODO implement
	}
	
	/**
	 * Adds an instruction
	 * @param opcode
	 * @param operands
	 */
	public void addOpcode(OpCodes opcode, Operand... operands) {
		// TODO implement
	}
	
	/**
	 * Writes the generated code into a stream
	 * @param path
	 * @throws IOException
	 */
	public void write(PrintStream stream) throws IOException {
		// TODO implement
	}
}
