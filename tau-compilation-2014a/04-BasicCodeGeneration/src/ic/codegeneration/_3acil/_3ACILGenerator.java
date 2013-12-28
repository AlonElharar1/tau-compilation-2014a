/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.codegeneration._3acil;

import ic.codegeneration._3acil.optimizers._3ACILOptimizer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class _3ACILGenerator {

	private int uniqueLabelId = 1;
	
	private HashMap<Label, Object> data = new LinkedHashMap<Label, Object>();
	private List<Instrucation> instrucations = new ArrayList<Instrucation>();
	
	public _3ACILGenerator() {
	}
	
	public List<Instrucation> getInstrucations() {
		return (this.instrucations);
	}
	
	/**
	 * Adds something to the data section
	 * @param data
	 */
	public void addData(Label label, String data) {
		this.data.put(label, data);
	}
	
	/**
	 * Adds something to the data section
	 * @param data
	 */
	public void addData(Label label, int data) {
		this.data.put(label, data);
	}
	
	/**
	 * Creates a new unique label
	 * @return
	 */
	public Label generateUniqueLabel() {
		return (new Label(String.format("unique%d", this.uniqueLabelId++)));
	}
	
	/**
	 * Adds an label
	 * @param label
	 */
	public void addLabel(Label label) {
		this.instrucations.add(new LabelInstrucation(label));
	}
	
	/**
	 * Adds an instruction
	 * @param opcode
	 * @param operands
	 */
	public void addOpcode(OpCodes opcode, Operand... operands) {
		if (opcode.getNumberOfOperands() != operands.length)
			throw new RuntimeException("operands number don't match the opcode");
		
		this.instrucations.add(new OpCodeInstrucation(opcode, operands));
	}
	
	/**
	 * Optimize the generated code so far
	 * @param optimizer
	 */
	public void optimize(_3ACILOptimizer optimizer) {
		optimizer.optimize(this);
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
