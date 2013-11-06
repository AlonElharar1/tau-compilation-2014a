package Lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

public class main {
	private static String readFile( String file ) throws IOException  {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }
	    
	    return stringBuilder.toString();
	    
	}
	
	public static void Main(String[] args){
		try{
			String str = new String(readFile(args[0]));
			Collection<Token> tokens = Lexer.processString(str);
		} catch (IOException e) { 
			System.out.println("error in file");
		} catch (Exception e) { 
			System.out.println("error in scanner");
		}
		
	}
	

}
