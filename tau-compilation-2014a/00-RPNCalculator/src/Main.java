/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import RPN.RPNCalculator;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String exp = "4 + 6 *";
		
		RPNCalculator calc = new RPNCalculator(exp);
		
		try {
			
			System.out.printf("%s : %s = %f" ,
					exp,
					calc.toInfix(), 
					calc.evaluate());
			
			
		} catch (Exception e) {
			
			System.out.printf("%s : invalid expresion", exp);
			
		}
		
	}

}
