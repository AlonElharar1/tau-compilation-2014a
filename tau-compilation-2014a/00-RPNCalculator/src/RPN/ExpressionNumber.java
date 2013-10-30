/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package RPN;

/**
 * Represents an expression leaf of a number
 */
public class ExpressionNumber extends ExpressionNode {

	public double value;
	
	public ExpressionNumber(double value) {
		super(null, null);
		this.value = value;
	}

	public ExpressionNumber(String valueStr) {
		this(Double.parseDouble(valueStr));
	}
	
	@Override
	public double getValue() {
		return (value);
	}

	@Override
	public String toInfixString() {
		return (Double.toString(this.getValue()));
	}

}
