package Lexer;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.LinkedList;

public class Lexer {
	
	public Collection<Token> processString(String input) throws Exception
	{
		Collection<Token> tokens = new LinkedList<Token>();
		JflexScanner scanner = new JflexScanner(new StringReader(input));
		
		
		Token token = scanner.yylex();
		while (token != null) {
			tokens.add(token);
            token = scanner.yylex();
        }
		return tokens;
	}
	
}
