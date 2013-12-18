/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.lexer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
	
	public static List<Token> getFileTokens(String file) throws LexicalException, IOException {
		
		if (file == null)
			return (null);
		
		// Open the file and extract tokens
		FileInputStream fileStream = null;
		List<Token> tokens = null;
		
		try {
			fileStream = new FileInputStream(file);
			tokens = new Lexer(fileStream).getAllTokens();
			fileStream.close();
		} finally {
			if (fileStream != null)
				fileStream.close();
		}
		
		return (tokens);
	}

}
