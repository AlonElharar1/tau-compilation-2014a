/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.lexer;

import fun.grammar.Word;

public class Token extends Word {
	
	private int line;
	private int column;
	private String text;
	
	public Token(String tag, int line, int column, String text)	{
		super(tag);
		this.line = line;
		this.column = column;
		this.text = text;
	}
	
	public String toString() {
		return (String.format("%s\t%s\t%2d%8d",
				this.text, this.tag, this.line, this.column));
	}
	
}