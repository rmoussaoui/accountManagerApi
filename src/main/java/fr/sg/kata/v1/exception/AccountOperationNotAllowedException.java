package fr.sg.kata.v1.exception;

public class AccountOperationNotAllowedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393164226372784392L;

	public AccountOperationNotAllowedException(String cause) {
		super(cause);
	}
	
	public AccountOperationNotAllowedException(String cause, Throwable ex) {
		super(cause, ex);
	}
}
