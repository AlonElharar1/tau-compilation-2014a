/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package Lexer;

import java.io.IOException;

public class LexicalException extends IOException {

	private static final long serialVersionUID = 2585248004446983615L;
	
	private int line;
	private int column;
	
	public LexicalException(int line, int column, String message) {
		super(String.format("%d:%d : lexical error; %s", line, column, message));

		this.line = line;
		this.column = column;
	}

}
