package ic;

@SuppressWarnings("serial")
public class IceCoffeException extends Error {

	public IceCoffeException(int line, int column, String type, String message) {
		super(createMessage(line, column, type, message));
	}
	
	public static String createMessage(int line, int column, String type, String message) {
		
		if (line == -1) { 
			return (String.format("at end of input : %s error; %s", type, message));
		}
		else {
			return (String.format("%d:%d : %s error; %s", line, column, type, message));
		}
		
	}
}
