/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.lexer;

import ic.IceCoffeException;

@SuppressWarnings("serial")
public class LexicalException extends IceCoffeException {
	
	public LexicalException(int line, int column, String message) {
		super(line, column, "lexical", message);
	}

}
