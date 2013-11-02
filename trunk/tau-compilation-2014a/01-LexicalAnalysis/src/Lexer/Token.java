/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package Lexer;

public class Token
{
	public String tag;
	public int line;
	public int column;
	public String text;
	
	public Token(String tag, int line, int column, String text)
	{
		this.tag = tag;
		this.line = line;
		this.column = column;
		this.text = text;
	}

	public String toString()
	{
		return String.format("%-13s%-13s%2d%8d",
				this.text, this.tag, this.line, this.column);
		
	}
}