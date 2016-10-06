package clueGame;

public class BadConfigFormatException extends Exception {
	private static String message;
	
	public BadConfigFormatException() {
		super("Config file not properly formatted.");
	}
	
	public BadConfigFormatException(String error) {
		super(error);
	}

}
