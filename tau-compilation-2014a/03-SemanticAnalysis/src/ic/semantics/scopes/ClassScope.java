/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics.scopes;

import ic.ast.Node;
import ic.ast.decl.DeclField;
import ic.ast.decl.DeclMethod;
import ic.ast.decl.DeclStaticMethod;
import ic.ast.decl.DeclVirtualMethod;
import ic.ast.expr.Ref;

import java.util.HashMap;

public class ClassScope extends IceCoffeScope {

	public HashMap<String, DeclStaticMethod> staticMethods = new HashMap<String, DeclStaticMethod>();
	public HashMap<String, DeclVirtualMethod> virtualMethods = new HashMap<String, DeclVirtualMethod>();
	public HashMap<String, DeclField> instanceFields = new HashMap<String, DeclField>();
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#findMethod(java.lang.String)
	 */
	@Override
	public DeclMethod findMethod(String methodName) {
		// TODO Auto-generated method stub
		return super.findMethod(methodName);
	}
	
	/* (non-Javadoc)
	 * @see ic.semantics.scopes.IceCoffeScope#findRef(ic.ast.expr.Ref)
	 */
	@Override
	public Node findRef(Ref location) {
		// TODO Auto-generated method stub
		return super.findRef(location);
	}
	
}
