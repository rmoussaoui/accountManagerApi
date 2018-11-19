package fr.sg.kata.v1.services;

import java.time.LocalDate;
import java.util.List;

import fr.sg.kata.v1.exception.AccountNotFoundException;
import fr.sg.kata.v1.models.Account;
import fr.sg.kata.v1.models.Transaction;

public interface IAccountService {
	
	/**
	 * Returns an account by its ID
	 * @param accountId
	 * @return
	 * @throws AccountNotFoundException
	 */
	public Account getAccountById(String accountId) throws AccountNotFoundException;
	
	/**
	 * Returns a list of transactions related to an account for a given dateRange.
	 * @param accountId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws AccountNotFoundException 
	 */
	public List<Transaction> getAccountHistory(String accountId, LocalDate startDate, LocalDate endDate) throws AccountNotFoundException;

}
