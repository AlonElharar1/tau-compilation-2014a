/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.codegeneration._3acil.optimizers;

import ic.codegeneration._3acil.Instrucation;
import ic.codegeneration._3acil.Label;
import ic.codegeneration._3acil.LabelInstrucation;
import ic.codegeneration._3acil.OpCodeInstrucation;
import ic.codegeneration._3acil.OpCodes;
import ic.codegeneration._3acil.Register;
import ic.codegeneration._3acil._3ACILGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ReuseRegistersOptimizer extends _3ACILOptimizer {

	private class CodeFlowNode {
		
		public Instrucation instruction;
		
		public List<CodeFlowNode> outEdges = new ArrayList<CodeFlowNode>();
		public List<CodeFlowNode> inEdges = new ArrayList<CodeFlowNode>();
		
		public Set<Register> regs;
		public Set<Register> use;
		public Set<Register> def;
		public Set<Register> in;
		public Set<Register> out;
		public Set<Register> alive;
		
		public CodeFlowNode(Instrucation instruction) {
			this.instruction = instruction;
			this.updateUseAndDef();
		}
		
		private void updateUseAndDef() {
			
			this.use = new TreeSet<Register>();
			this.def = new TreeSet<Register>();
			
			if (!(this.instruction instanceof OpCodeInstrucation))
				return;
			
			OpCodeInstrucation opcodeInstrucation = 
					(OpCodeInstrucation)this.instruction;
			
			// Uses
			for (Integer index : opcodeInstrucation.getOpcode().getUseOperators()) {
				if (opcodeInstrucation.getOperands()[index] instanceof Register) {
					this.use.add((Register)opcodeInstrucation.getOperands()[index]);
				}
			}
			
			// Defines
			for (Integer index : opcodeInstrucation.getOpcode().getDefOperators()) {
				if (opcodeInstrucation.getOperands()[index] instanceof Register) {
					this.def.add((Register)opcodeInstrucation.getOperands()[index]);
				}
			}
			
			// Registers
			this.regs = new TreeSet<Register>();
			this.regs.addAll(this.use);
			this.regs.addAll(this.def);
		}

		public void addOutEdge(CodeFlowNode node) {
			this.outEdges.add(node);
			node.inEdges.add(this);
		}
		
		public boolean hasFallThroughInstruction() {
			if (!(this.instruction instanceof OpCodeInstrucation)) {
				return (true);
			}
			else {
				List<OpCodes> notfallThroughOpcodes = Arrays.asList(new OpCodes[] 
						{ 
							OpCodes.RET, OpCodes.RETVAL, OpCodes.GOTO
						});
				
				return (!notfallThroughOpcodes.contains(
							((OpCodeInstrucation)this.instruction).getOpcode()));
			}
		}
	
		public void replaceRegister(Register oldReg, Register newReg) {
			
			if (!(this.instruction instanceof OpCodeInstrucation))
				return;
			
			OpCodeInstrucation opcodeInstrucation = 
					(OpCodeInstrucation)this.instruction;
			
			for (int i = 0; i < opcodeInstrucation.getOperands().length; i++) {
				if (opcodeInstrucation.getOperands()[i].equals(oldReg)) {
					opcodeInstrucation.getOperands()[i] = newReg;
				}
			}
			
			this.updateUseAndDef();
		}
	}
	
	private class CodeFlowGraph implements Iterable<CodeFlowNode> {
		public List<CodeFlowNode> nodes = new ArrayList<CodeFlowNode>();
		
		public CodeFlowGraph(List<Instrucation> instructions) {
			this.build(instructions);
		}

		public CodeFlowGraph() {
		}
		
		private CodeFlowNode findLabelNode(Label label) {
			
			for (CodeFlowNode node : this.nodes) {
				if ((node.instruction instanceof LabelInstrucation) &&
					((LabelInstrucation)node.instruction).getLabel().equals(label)) {
					return (node);
				}
			}
			
			return (null);
		}
		
		private void build(List<Instrucation> instructions) {
			
			if (instructions.isEmpty())
				return;
			
			// Create each instruction node
			for (Instrucation instrucation : instructions) {
				this.nodes.add(new CodeFlowNode(instrucation));
			}
			
			// Set nodes edges
			for (int i = 0; i < this.nodes.size(); i++) {
				
				CodeFlowNode currNode = this.nodes.get(i);
				
				// Fall through
				if (((i + 1) < this.nodes.size()) && 
					currNode.hasFallThroughInstruction()) {
					currNode.addOutEdge(this.nodes.get(i + 1));
				}
				
				// Jumps
				if (currNode.instruction instanceof OpCodeInstrucation) {
					OpCodeInstrucation opcodeInstruction = (OpCodeInstrucation)currNode.instruction;
					
					switch (opcodeInstruction.getOpcode()) {
					case GOTO:
						currNode.addOutEdge(this.findLabelNode(
								(Label)opcodeInstruction.getOperands()[0]));
						break;
					case IF:
					case NIF:
						currNode.addOutEdge(this.findLabelNode(
								(Label)opcodeInstruction.getOperands()[1]));
						break;
					case IFE:
					case IFNE:
						currNode.addOutEdge(this.findLabelNode(
								(Label)opcodeInstruction.getOperands()[3]));
						break;
					default:
						break;
					}
				}
			}
		}

		public void liveAnalysis() {

			for (CodeFlowNode node : this.nodes) {
				node.out = new TreeSet<Register>();
				node.in = new TreeSet<Register>();
			}
			
			Queue<CodeFlowNode> workQueue = 
					new LinkedList<CodeFlowNode>(this.nodes);
			
			while (!workQueue.isEmpty()) {

				CodeFlowNode node = workQueue.poll();
				
				node.out = new TreeSet<Register>();
				for (CodeFlowNode succ : node.outEdges) {
					node.out.addAll(succ.in);
				}
				
				Set<Register> newIn = new TreeSet<Register>();
				newIn.addAll(node.out);
				newIn.removeAll(node.def);
				newIn.addAll(node.use);
				
				if (!node.in.equals(newIn)) {
					for (CodeFlowNode pred : node.inEdges) {
						workQueue.add(pred);
					}
					
					node.in = newIn;
				}
			}
			
			for (CodeFlowNode node : this.nodes) {
				node.alive = new TreeSet<Register>();
				node.alive.addAll(node.in);
				node.alive.addAll(node.out);
			}
		}

		public List<CodeFlowGraph> getComponents() {
			List<CodeFlowGraph> components = new ArrayList<CodeFlowGraph>();
			
			CodeFlowGraph currComponent = null;
			
			for (int i = 0; i < this.nodes.size(); i++) {

				CodeFlowNode currNode = this.nodes.get(i);
				
				boolean newComponent = true;

				for (CodeFlowNode inNode : currNode.inEdges) {
					if (this.nodes.indexOf(inNode) < i) {
						newComponent = false;
						break;
					}
				}
				
				if (newComponent) {
					currComponent = new CodeFlowGraph();
					components.add(currComponent);
				}
				
				currComponent.nodes.add(currNode);
			}
			
			return (components);
		}
		
		@SuppressWarnings("unused")
		public void print() {
			
			for (CodeFlowNode node : this.nodes) {
				System.out.println(node.instruction.generateString());

				System.out.println("\t\t===== In =====");
				for (Register reg : node.in) {
					System.out.println("\t\t" + reg.getOperandString());
				}
				
				System.out.println("\t\t===== Out =====");
				for (Register reg : node.out) {
					System.out.println("\t\t" + reg.getOperandString());
				}
			}
		}

		@Override
		public Iterator<CodeFlowNode> iterator() {
			return (nodes.iterator());
		}
	}
	
	private void reuseRegisters(CodeFlowGraph cfg) {
		
		List<CodeFlowGraph> components = cfg.getComponents();
	
		for (CodeFlowGraph currCfg : components) {
			
			final Map<Register, Integer> firstSeen = new TreeMap<Register, Integer>();
			Map<Register, Integer> lastSeen = new TreeMap<Register, Integer>();
			this.firstLastSeens(currCfg, firstSeen, lastSeen);
			
			LinkedList<Register> registers = new LinkedList<Register>(firstSeen.keySet());
			Collections.sort(registers, new Comparator<Register>() {
					@Override
					public int compare(Register o1, Register o2) {
						return (firstSeen.get(o1).compareTo(firstSeen.get(o2)));
					}
				});
			
			while (!registers.isEmpty()) {
				
				Register currReg = registers.pollFirst();
				
				// Post-seen expand
				Register overReg = null;
				
				for (Register reg : firstSeen.keySet()) {
					if ((firstSeen.get(reg) > lastSeen.get(currReg)) &&
						((overReg == null) || (firstSeen.get(overReg) > firstSeen.get(reg)))) {
						overReg = reg;
					}
				}
				
				if (overReg != null) {

					for (int i = firstSeen.get(overReg); i <= lastSeen.get(overReg); i++) {
						currCfg.nodes.get(i).replaceRegister(overReg, currReg);
					}
					
					lastSeen.put(currReg, lastSeen.get(overReg));
					
					firstSeen.remove(overReg);
					lastSeen.remove(overReg);
					registers.remove(overReg);
					
					registers.addFirst(currReg);
				}	
			}
		}
		
		// Update graph
		cfg.liveAnalysis();
	}

	private void firstLastSeens(CodeFlowGraph currCfg, Map<Register, Integer> firstSeen, Map<Register, Integer> lastSeen) {

		// First Seen
		for (int nodeIndex = 0; nodeIndex < currCfg.nodes.size(); nodeIndex++) {
			CodeFlowNode currNode = currCfg.nodes.get(nodeIndex); 
			
			for (Register reg : currNode.alive) {
				if (!firstSeen.containsKey(reg))
					firstSeen.put(reg, nodeIndex);
			}
		}
		
		// Last Seen
		for (int nodeIndex = currCfg.nodes.size() - 1; nodeIndex >= 0; nodeIndex--) {
			CodeFlowNode currNode = currCfg.nodes.get(nodeIndex); 
			
			for (Register reg : currNode.alive) {
				if (!lastSeen.containsKey(reg))
					lastSeen.put(reg, nodeIndex);
			}
		}
		
	}
	
	@Override
	public void optimize(_3ACILGenerator generator) {
		CodeFlowGraph cfg = new CodeFlowGraph(generator.getInstrucations());
		cfg.liveAnalysis();
		this.reuseRegisters(cfg);
	}
}
