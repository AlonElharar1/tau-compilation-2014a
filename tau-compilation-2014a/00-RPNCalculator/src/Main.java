/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import RPN.RPNParser;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String exp = "4 + 6 *";
		
		RPNParser parser = new RPNParser(exp);
		
		try {
		
			parser.parse();
			
			System.out.printf("%s : %s = %f" ,
					exp,
					parser.toInfix(), 
					parser.evaluate());
			
			
		} catch (Exception e) {
			
			System.out.printf("%s : invalid expresion", exp);
			
		}
		
	}

}
