package fr.sg.kata.v1.exception;

public class AccountNotFoundException extends Exception {
	
	private static final long serialVersionUID = 5740470142535958302L;

	public AccountNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AccountNotFoundException(String message) {
		super(message);
	}

}
