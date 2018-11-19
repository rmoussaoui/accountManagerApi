package fr.sg.kata.v1.services;

public interface IMessageService {
	
	/**
	 * Returns a message by its code
	 * @param code
	 * @return
	 */
	String getMessage(String code);
	
	/**
	 * Returns a message bi its code using parameters
	 * @param code
	 * @param args
	 * @return
	 */
	String getMessage(String code, Object[] args);

}
