/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.lexer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Lexer {

	private InputStream stream;
	private JflexScanner jflexScanner;

	public Lexer(InputStream stream) {
		this.stream = stream;
		jflexScanner = new JflexScanner(this.stream);
	}

	public Token nextToken() throws IOException, LexicalException {
		return (this.jflexScanner.yylex());
	}

	public List<Token> getAllTokens() throws IOException, LexicalException {
		
		ArrayList<Token> tokens = new ArrayList<Token>();
		
		Token token = this.nextToken();

		while (token != null) {
			tokens.add(token);
			token = this.nextToken();
		}
		
		return (tokens);
	}

}
