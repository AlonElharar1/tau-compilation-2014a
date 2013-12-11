/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.interpreter;

import ic.IceCoffeException;

/**
 * @author Nir
 *
 */
@SuppressWarnings("serial")
public class InterpreterRunTimeException extends IceCoffeException {

	/**
	 * @param line
	 * @param column
	 * @param type
	 * @param message
	 */
	public InterpreterRunTimeException(int line, int column, String type,
			String message) {
		super(line, column, type, message);
		// TODO Auto-generated constructor stub
	}

}
