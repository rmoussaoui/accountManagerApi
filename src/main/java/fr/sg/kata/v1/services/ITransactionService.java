package fr.sg.kata.v1.services;

import fr.sg.kata.v1.data.TransactionRequestData;
import fr.sg.kata.v1.exception.AccountNotFoundException;
import fr.sg.kata.v1.exception.InvalidTransactionAmountException;
import fr.sg.kata.v1.models.Transaction;

public interface ITransactionService {
	
	
	/**
	 * Performs a transaction related to an account
	 * @param transactionRequestData
	 * @param accountId
	 * @return a transaction
	 * @throws AccountNotFoundException
	 * @throws InvalidTransactionAmountException
	 */
	public Transaction doTransaction(TransactionRequestData transactionRequestData, String accountId) throws AccountNotFoundException, InvalidTransactionAmountException;

}
