/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.semantics;

import ic.IceCoffeException;

@SuppressWarnings("serial")
public class SemanticException extends IceCoffeException {

	/**
	 * @param line
	 * @param column
	 * @param type
	 * @param message
	 */
	public SemanticException(int line, String message) {
		super(line, 0, "semantic", message);
	}

}
