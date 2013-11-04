/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package Lexer;

public class Token
{
	private final String ERROR = "error";
	
	private String tag;
	private int line;
	private int column;
	private String text;
	
	public Token(String tag, int line, int column, String text)
	{
		this.tag = tag;
		this.line = line;
		this.column = column;
		this.text = text;
	}
	

	public String toString()
	{
		if(this.tag != ERROR)
		{
			return String.format("%-13s%-13s%2d%8d",
					this.text, this.tag, this.line, this.column);
		}
		
		return String.format("%d:%d : %s",
				this.line, this.column, this.text);
	}
}