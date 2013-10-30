/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import RPN.RPNParser;

public class Main {
	
	public static void readAndWrite(BufferedReader input) throws IOException
	{
		String exp;
		
		while((exp = input.readLine()) != null)
		{
			try {
				RPNParser parser = new RPNParser(exp);	
				parser.parse();
				
				System.out.printf("%s : %s = %.1f\n",
						exp,
						parser.toInfixString(), 
						parser.evaluate()
						);
				
				
			} catch (Exception e) {
				
				System.out.printf("%s : invalid expression\n", exp);
				
			}
		}
	}
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		try {
			BufferedReader input = new BufferedReader(new FileReader(args[0]));
			readAndWrite(input);
			input.close();
			
		} catch (IOException e) {
			System.out.println(e);
		}
		
		
	}

}
