/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration;

import ic.codegeneration._3acil.Immediate;
import ic.codegeneration._3acil.Label;
import ic.codegeneration._3acil.OpCodes;
import ic.codegeneration._3acil.Operand;
import ic.codegeneration._3acil.Register;
import ic.codegeneration._3acil._3ACILGenerator;

public class RunTimeChecksGenerator {

	private _3ACILGenerator generator;
	
	public RunTimeChecksGenerator(_3ACILGenerator generator) {
		this.generator = generator;
	}

	public void emitNullCheck(Operand ptr) {
		
		Label endCheckLabel = this.generator.generateUniqueLabel("endCheck");

		this.generator.addOpcode(OpCodes.IF, ptr, endCheckLabel);
		
		this.exitWithError("Null pointer dereference!");
		
		this.generator.addLabel(endCheckLabel);
	}

	private void exitWithError(String msg) {
		if (msg != null) {
			this.generator.addOpcode(OpCodes.PARAM, 
					this.generator.addString(String.format("Runtime Error: %s", msg)));
			this.generator.addOpcode(OpCodes.CALL,new Label("println"));
		}
		
		this.generator.addOpcode(OpCodes.PARAM, new Immediate(1));
		this.generator.addOpcode(OpCodes.CALL,new Label("exit"));
	}

	public void emitArrayIndexCheck(Operand array, Operand index) {
		
		Register size = this.generator.getFreeRegister();
		this.generator.addOpcode(OpCodes.READ, array, size);
		
		Label errorLabel = this.generator.generateUniqueLabel("error");
		Label endCheckLabel = this.generator.generateUniqueLabel("endCheck");
		
		Register firstCheckResult = this.generator.getFreeRegister();
		this.generator.addOpcode(OpCodes.GTE, index, new Immediate(0), firstCheckResult);
		this.generator.addOpcode(OpCodes.NIF, firstCheckResult, errorLabel);
		
		Register secondCheckResult = this.generator.getFreeRegister();
		this.generator.addOpcode(OpCodes.GTE, index, size, secondCheckResult);
		this.generator.addOpcode(OpCodes.NIF, secondCheckResult, endCheckLabel);
		
		this.generator.addLabel(errorLabel);
		this.exitWithError("Array index out of bounds!");
		this.generator.addLabel(endCheckLabel);
	}
	
	public void emitArraySizeCheck(Operand size) {
		
		Label endCheckLabel = this.generator.generateUniqueLabel("endCheck");
		
		Register result = this.generator.getFreeRegister();
		this.generator.addOpcode(OpCodes.GTE, size, new Immediate(0),result);
		this.generator.addOpcode(OpCodes.IF, result, endCheckLabel);
		
		this.exitWithError("Array allocation with negative array size!");
		this.generator.addLabel(endCheckLabel);
	}
	
	public void emitDivisionByZeroCheck(Operand val) {
		
		Label endCheckLabel = this.generator.generateUniqueLabel("endCheck");
		this.generator.addOpcode(OpCodes.IF, val, endCheckLabel);
		this.exitWithError("Division by zero!");
		this.generator.addLabel(endCheckLabel);
	}
	
}
