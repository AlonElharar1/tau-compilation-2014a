/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration;

import ic.codegeneration._3acil.Operand;
import ic.codegeneration._3acil.Register;
import ic.codegeneration._3acil._3ACILGenerator;

public class RunTimeChecksGenerator {

	private _3ACILGenerator generator;
	
	public RunTimeChecksGenerator(_3ACILGenerator generator) {
		this.generator = generator;
	}

	public void emitNullCheck(Operand ptr) {
		// TODO implement
	}
	
	public void emitArrayIndexCheck(Operand array, Operand index) {
		// TODO implement
	}
	
	public void emitArraySizeCheck(Operand size) {
		// TODO implement
	}
	
	public void emitDivisionByZeroCheck(Operand val) {
		// TODO implement
	}
	
}
