/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.codegeneration;

import ic.ast.RunThroughVisitor;
import ic.ast.decl.DeclClass;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclVirtualMethod;
import ic.codegeneration._3acil.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VirtualAnalyzer extends RunThroughVisitor {
	
	private HashMap<DeclClass, Integer> classesSizes = new HashMap<DeclClass, Integer>();
	private HashMap<DeclField, Integer> fieldsOffsets = new HashMap<DeclField, Integer>();
	private HashMap<DeclVirtualMethod, Integer> methodsOffsets = new HashMap<DeclVirtualMethod, Integer>();

	private HashMap<DeclClass, List<Label>> dispachVectors = new HashMap<DeclClass, List<Label>>();
	
	public Integer sizeOf(DeclClass icClass) {
		return (this.classesSizes.get(icClass));
	}
	
	public Integer getOffset(DeclField field) {
		return (this.fieldsOffsets.get(field));
	}
	
	public Integer getOffset(DeclVirtualMethod method) {
		return (this.methodsOffsets.get(method));
	}
	
	public List<Label> getDispachVector(DeclClass icClass) {
		return (this.dispachVectors.get(icClass));
	}
	
	@Override
	public Object visit(DeclClass icClass) {
		
		DeclClass superClass = icClass.getSuperClass();
				
		// Set fields offsets
		Integer lastFieldOffset = icClass.hasSuperClass() ? this.sizeOf(superClass) - 1 : 0;
				
		for (DeclField field : icClass.getFields()) {
			this.fieldsOffsets.put(field, ++lastFieldOffset);
		}
		
		// Set methods offsets and create the dispatch vector
		List<Label> dispachVector = icClass.hasSuperClass() ?
				new ArrayList<Label>(this.getDispachVector(superClass)) : new ArrayList<Label>();
				
		for (DeclMethod method : icClass.getMethods()) {
			if (method instanceof DeclVirtualMethod) {
				DeclVirtualMethod currMethod = (DeclVirtualMethod)method;
				Label methodLabel = new Label(currMethod.getId());
				
				if (currMethod.isOverriding()) {
					dispachVector.set(
							this.getOffset(currMethod.getOverridenMethod()),
							methodLabel);
				}
				else {
					dispachVector.add(methodLabel);
					this.methodsOffsets.put(currMethod, dispachVector.size() - 1);
				}
			}
		}
		
		this.dispachVectors.put(icClass, dispachVector);
		
		// Set class size
		this.classesSizes.put(icClass, 1 + lastFieldOffset);
		
		return (null);
	}

}
