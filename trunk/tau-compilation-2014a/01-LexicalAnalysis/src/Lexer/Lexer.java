/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package Lexer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Collection;
import java.util.LinkedList;

public class Lexer {
	
	private InputStream stream;
	private JflexScanner jflexScanner;
	
	public Lexer(InputStream stream) {
		this.stream = stream;
		jflexScanner = new JflexScanner(this.stream);
	}

	public Token nextToken() throws IOException {
		return (this.jflexScanner.yylex());
	}
	
}
