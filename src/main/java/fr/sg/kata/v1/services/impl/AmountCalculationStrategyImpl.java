package fr.sg.kata.v1.services.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.sg.kata.v1.exception.InvalidTransactionAmountException;
import fr.sg.kata.v1.models.TransactionType;
import fr.sg.kata.v1.services.IAmountCalculationStrategy;
import fr.sg.kata.v1.services.IMessageService;


@Service
public class AmountCalculationStrategyImpl implements IAmountCalculationStrategy {

	@Autowired
	private IMessageService messageService;
	
	@Override
	public BigDecimal computeBalanceAmount(TransactionType transactionType, BigDecimal balance, BigDecimal amount) throws InvalidTransactionAmountException {
		if (TransactionType.D.equals(transactionType)) {
			balance = balance.add(amount);
		}
		else if (TransactionType.W.equals(transactionType)) {
			if (balance.compareTo(amount) < 0) {
				throw new InvalidTransactionAmountException(messageService.getMessage("transaction.amount.not.greater.account.balance", new String[] {String.valueOf(balance)}));
			}

			balance = balance.subtract(amount);
		}
		
		return balance;
	}

}
