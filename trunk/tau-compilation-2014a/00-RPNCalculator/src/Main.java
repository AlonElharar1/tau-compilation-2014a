/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import RPN.RPNCalculator;
import RPN.RPNConverter;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		RPNCalculator calc = new RPNCalculator();
		RPNConverter converter = new RPNConverter();
		
		String exp = "4 + 6 *";
		
		try {
			
			
			
			System.out.printf("%s : %s = %f" ,
					exp,
					converter.toInfix(exp), 
					calc.evaluate(exp));
			
			
		} catch (Exception e) {
			
			System.out.printf("%s : invalid expresion", exp);
			
		}
		
	}

}
