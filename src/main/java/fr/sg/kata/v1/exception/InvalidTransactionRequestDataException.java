package fr.sg.kata.v1.exception;

public class InvalidTransactionRequestDataException extends Exception {
	
	private static final long serialVersionUID = 5287495340488625764L;

	public InvalidTransactionRequestDataException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidTransactionRequestDataException(String message) {
		super(message);
	}


}
