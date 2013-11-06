/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Scanner;

import Lexer.*;

public class Main 
{
	public static void main(String[] args) throws Exception 
	{	
		// Validate arguments
		if (args.length < 1)
		{
			System.out.println("Not cool man... i need a file!");
			return;
		}
		
		FileInputStream fileStream = null;
		
		try 
		{
			// Open the file
			fileStream = new FileInputStream(args[0]);
			
			//Print header
			System.out.println(String.format(
					"%-13s%-13s%s : %s",
					"token","tag","line","column"));
			
			// Process the given file using jflex
			Lexer lexer = new Lexer(fileStream);

			Token token = lexer.nextToken();
			
			while (token != null) {
				System.out.println(token);
				
				token = lexer.nextToken();
			}
		}
		catch (LexicalException e)	{
			System.out.println(e.getMessage());
		}
		catch (IOException e) {
			System.out.println("Error reading file...");
		}
		finally {
			if (fileStream != null) 
				fileStream.close();
		}
	}

}
