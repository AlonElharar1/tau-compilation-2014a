/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.syntax;

import fun.parser.earley.EarleyParser.PostMortem;
import ic.IceCoffeException;
import ic.lexer.Token;

@SuppressWarnings("serial")
public class SyntaxException extends IceCoffeException {
	
	public SyntaxException(int line, int column, String message) {
		super(line, column, "syntax", message);
	}

	public SyntaxException(PostMortem mortem) {
		super(mortem.token != null ? ((Token)mortem.token).line : -1,
			  mortem.token != null ? ((Token)mortem.token).column : -1, 
			  "syntax", generateMessage(mortem));
	}

	private static String generateMessage(PostMortem mortem) {
		
		StringBuilder strbuilder = new StringBuilder();
		
		strbuilder.append("expected ");
		
		for (String expected : mortem.expecting) {
			strbuilder.append(String.format("'%s' or ", expected)); 
		}
		
		strbuilder.delete(strbuilder.length() - " or ".length(), strbuilder.length());
		
		Token mortemToken = (Token)mortem.token;
		
		if (mortemToken != null)
			strbuilder.append(String.format(", but found '%s'", mortemToken.text));
		
		return (strbuilder.toString());
	}
}
