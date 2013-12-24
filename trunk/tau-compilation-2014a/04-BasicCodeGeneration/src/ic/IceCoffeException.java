package ic;

@SuppressWarnings("serial")
public class IceCoffeException extends Error {

	public static final int END_OF_INPUT = -1;
	public static final int UNKNOWN = -1;
	
	public IceCoffeException(int line, int column, String type, String message) {
		super(createMessage(line, column, type, message));
	}
	
	private static String createMessage(int line, int column, String type, String message) {
		
		if (line == END_OF_INPUT) { 
			return (String.format("at end of input : %s error; %s", type, message));
		}
		else if (column == UNKNOWN) {
			return (String.format("%d: %s error; %s", line, type, message));
		}
		else {
			return (String.format("%d:%d: %s error; %s", line, column, type, message));
		}
		
	}
}
