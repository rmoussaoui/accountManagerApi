package fr.sg.kata.v1.exception;

public class InvalidTransactionAmountException extends Exception {

	private static final long serialVersionUID = 2841387753251352145L;

	public InvalidTransactionAmountException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidTransactionAmountException(String message) {
		super(message);
	}

}
