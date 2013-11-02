/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import java.io.File;
import java.util.Collection;
import java.util.Scanner;

import Lexer.*;

public class Main 
{

	public static void main(String[] args) throws Exception 
	{	
		try 
		{
			//Read entire file to a single String.
			File inputFile = new File(args[0]);
			Scanner scanner = new Scanner(inputFile);
			String input = scanner.useDelimiter("\\Z").next();

			//Process the String.
			Lexer lexer = new Lexer();
			Collection<Token> tokens = lexer.processString(input);
			
			//Print header
			System.out.println(String.format(
					"%-13s%-13s%s : %s",
					"token","tag","line","column"));
			scanner.close();

			//Print tokens
			for(Token token : tokens)
			{
				System.out.println(token);
			}
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}	
	}

}
