/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.interpreter;

import ic.IceCoffeException;

@SuppressWarnings("serial")
public class InterpreterRunTimeException extends IceCoffeException {

	/**
	 * @param line
	 * @param column
	 * @param type
	 * @param message
	 */
	public InterpreterRunTimeException(int line, String message) {
		super(line, IceCoffeException.UNKNOWN, "run time", message);
	}

}
