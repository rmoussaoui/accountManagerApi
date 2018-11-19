package fr.sg.kata.v1.services;

import java.math.BigDecimal;

import fr.sg.kata.v1.exception.InvalidTransactionAmountException;
import fr.sg.kata.v1.models.TransactionType;

public interface IAmountCalculationStrategy {
	
	/**
	 * Computes a new balance basd on a transaction amount and type
	 * @param transactionType
	 * @param balance
	 * @param amount
	 * @return
	 * @throws InvalidTransactionAmountException 
	 */
	BigDecimal computeBalanceAmount(TransactionType transactionType, BigDecimal balance, BigDecimal amount) throws InvalidTransactionAmountException;
}
