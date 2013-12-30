/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.codegeneration._3acil;

import ic.codegeneration._3acil.optimizers._3ACILOptimizer;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;

public class _3ACILGenerator {

	private int uniqueLabelId = 1;
	
	private HashMap<Object, Label> data = new LinkedHashMap<Object, Label>();
	private List<Instrucation> instrucations = new ArrayList<Instrucation>();
	
	private Stack<Integer> freeRegisterStack = new Stack<Integer>();
	
	public _3ACILGenerator() {
	}
	
	public List<Instrucation> getInstrucations() {
		return (this.instrucations);
	}
	
	/**
	 * Adds a string to the data section
	 * @param data
	 */
	public Label addString(String data) {
		
		if (this.data.containsKey(data))
			return (this.data.get(data));
		
		Label strLabel = this.generateUniqueLabel("str");
		this.data.put(data, strLabel);
		
		return (strLabel);
	}
	
	/**
	 * Creates a new unique label
	 * @return
	 */
	public Label generateUniqueLabel(String prefix) {
		return (new Label(String.format("%s%d", prefix, this.uniqueLabelId++)));
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

	public Operand addGetInstruction(Object data) {
		if (data instanceof Operand)
			return (this.addGetInstruction((Operand)data));
		
		throw new RuntimeException("invalid arguments. only operands allowed");
	}
	
	public Operand addGetInstruction(Operand data) {
		
		if (data instanceof MemoryLocation) {
			
			Register dest = this.getFreeRegister();
			this.addOpcode(OpCodes.READ, ((MemoryLocation)data).getAddress(), dest);
			
			return (dest);
		}
		else {
			return (data);
		}
	}
	
	public void addSetInstruction(Object src, Object dest) {
		if ((src instanceof Operand) && (dest instanceof Operand))
			this.addSetInstruction((Operand)src, (Operand)dest);
		else {
			throw new RuntimeException("invalid arguments. only operands allowed");
		}
	}
	
	public void addSetInstruction(Operand src, Operand dest) {
		
		Operand srcAfterGet = this.addGetInstruction(src);
		
		if (dest instanceof Register) {
			this.addOpcode(OpCodes.MOV, srcAfterGet, dest);
		}
		else if (dest instanceof MemoryLocation) {
			this.addOpcode(OpCodes.WRITE, ((MemoryLocation)dest).getAddress(), srcAfterGet);
		}
		else {
			throw new RuntimeException("invalid dest operand");
		}
	}
	
	public Register getFreeRegister() {
		Register reg = new Register(this.freeRegisterStack.peek());
		this.freeRegisterStack.push(this.freeRegisterStack.pop() + 1);
		return (reg);
	}
	
	public void startNewRegisterContext() {
		this.freeRegisterStack.push(0);
	}
	
	public void endRegisterContext() {
		this.freeRegisterStack.pop();
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
	public void write(PrintStream stream) {
		
		// Code section
		for (Instrucation instruction : this.instrucations) {
			stream.println(instruction.generateString());
		}
		
		// Data section
		stream.println(".data");
		
		for (Object data : this.data.keySet()) {
			
			Label dataLabel = this.data.get(data);
			
			stream.println(dataLabel.getOperandString());
			
			if (data instanceof Integer) {
				stream.println("\t" + data.toString());
			}
			else {
				String dataStr = data.toString();
				stream.println("\t" + dataStr.length());
				stream.println(String.format("\t\"%s\"", dataStr));
			}
		}
	}
}
