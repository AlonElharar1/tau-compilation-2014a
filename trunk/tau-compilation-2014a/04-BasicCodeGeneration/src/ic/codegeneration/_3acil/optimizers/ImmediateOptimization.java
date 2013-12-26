/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.codegeneration._3acil.optimizers;

import ic.codegeneration._3acil.Immediate;
import ic.codegeneration._3acil.Instrucation;
import ic.codegeneration._3acil.OpCodeInstrucation;
import ic.codegeneration._3acil.OpCodes;
import ic.codegeneration._3acil.Register;
import ic.codegeneration._3acil._3ACILGenerator;

public class ImmediateOptimization extends _3ACILOptimizer {

	@Override
	public void optimize(_3ACILGenerator generator) {
		
		// TODO MEGA refactor ImmediateOptimization!!!
		
		for (int i = 0; i < generator.getInstrucations().size(); i++) {
			
			// Check if the instruction is setting a register with an immediate
			if (generator.getInstrucations().get(i) instanceof OpCodeInstrucation) {
				
				OpCodeInstrucation immediateInstruction = 
						(OpCodeInstrucation)generator.getInstrucations().get(i);
				
				if ((immediateInstruction.getOpcode() == OpCodes.MOV) &&
					(immediateInstruction.getOperands()[0] instanceof Register) &&
					(immediateInstruction.getOperands()[1] instanceof Immediate)) {
					
					boolean registerChanged = false;
					
					for (int j = i + 1; 
						 (j < generator.getInstrucations().size()) && 
						  !registerChanged;
						 j++) {

						if (generator.getInstrucations().get(j) instanceof OpCodeInstrucation) {
							
							OpCodeInstrucation replaceInstruction = 
									(OpCodeInstrucation)generator.getInstrucations().get(j);
							
							switch (replaceInstruction.getOpcode()) {
							case GOTO:
								registerChanged = true; 
								break;
							case ADD:
							case SUB:
							case MUL:
							case DIV:
							case MOD:
							case EQ:
							case NEQ:
							case GT:
							case GTE:
							case LT:
							case LTE:
							case AND:
							case OR:
								
								if (replaceInstruction.getOperands()[0].equals(
										immediateInstruction.getOperands()[0])) {
									replaceInstruction.getOperands()[0] = 
											immediateInstruction.getOperands()[1];
								} 
								else if (replaceInstruction.getOperands()[1].equals(
										immediateInstruction.getOperands()[0])) {
									replaceInstruction.getOperands()[0] = 
											immediateInstruction.getOperands()[1];
								}
								else if (replaceInstruction.getOperands()[2].equals(
										immediateInstruction.getOperands()[0])) {
									registerChanged = true;
								}
								
								break;
							default:
								break;
							}
						}
					}
				}
			}
		}
	}

}
