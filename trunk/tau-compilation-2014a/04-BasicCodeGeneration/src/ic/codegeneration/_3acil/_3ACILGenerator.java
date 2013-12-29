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
	
	private HashMap<Label, Object> data = new LinkedHashMap<Label, Object>();
	private List<Instrucation> instrucations = new ArrayList<Instrucation>();
	
	private Stack<Integer> freeRegisterStack = new Stack<Integer>();
	
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
		this.data.put(label, new Integer(data));
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

	public Register addGetInstruction(Object data) {
		if (data instanceof Operand)
			return (this.addGetInstruction((Operand)data));
		
		throw new RuntimeException("invalid arguments. only operands allowed");
	}
	
	public Register addGetInstruction(Operand data) {
		
		if (data instanceof Register) {
			return ((Register)data);
		}
		
		Register dest = this.getFreeRegister();
		
		if (data instanceof MemoryLocation) {
			this.addOpcode(OpCodes.READ, ((MemoryLocation)data).getAddress(), dest);
		}
		else {
			this.addOpcode(OpCodes.MOV, data, dest);
		}
		
		return (dest);
	}
	
	public void addSetInstruction(Object src, Object dest) {
		if ((src instanceof Operand) && (dest instanceof Operand))
			this.addSetInstruction((Operand)src, (Operand)dest);
		else {
			throw new RuntimeException("invalid arguments. only operands allowed");
		}
	}
	
	public void addSetInstruction(Operand src, Operand dest) {
		
		Register srcReg = this.addGetInstruction(src);
		
		if (dest instanceof Register) {
			this.addOpcode(OpCodes.MOV, srcReg, dest);
		}
		else if (dest instanceof MemoryLocation) {
			this.addOpcode(OpCodes.WRITE, ((MemoryLocation)dest).getAddress(), srcReg);
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
		this.freeRegisterStack.push(1);
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
		
		for (Label dataLabel : this.data.keySet()) {
			stream.println(dataLabel.getOperandString());
			
			Object data = this.data.get(dataLabel);
			
			if (data instanceof Integer) {
				stream.println(data.toString());
			}
			else {
				String dataStr = data.toString();
				stream.println(dataStr.length());
				stream.println(String.format("\"%s\"", dataStr));
			}
		}
	}
}
