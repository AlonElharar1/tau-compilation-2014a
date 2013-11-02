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
	public int start;
	public int end;
	public String text;
	
	public Token(String tag, int start, int end, String text)
	{
		this.tag = tag;
		this.start = start;
		this.end = end;
		this.text = text;
	}
	
	public Token(String tag, int start, String text)
	{
		this(tag, start, start + text.length(), text);
	}	
	
	public String toString()
	{
		return String.format("%s\t%s\t%d\t%d",
				this.text, this.tag, this.start, this.end);
		
	}
}