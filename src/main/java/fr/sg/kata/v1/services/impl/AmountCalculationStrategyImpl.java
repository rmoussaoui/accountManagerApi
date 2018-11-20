package fr.sg.kata.v1.services.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.sg.kata.v1.exception.InvalidTransactionAmountException;
import fr.sg.kata.v1.models.TransactionType;
import fr.sg.kata.v1.services.IAmountCalculationStrategy;
import fr.sg.kata.v1.services.IMessageService;


@Service
public class AmountCalculationStrategyImpl implements IAmountCalculationStrategy {
	
	Logger logger = LoggerFactory.getLogger(AmountCalculationStrategyImpl.class);

	@Autowired
	private IMessageService messageService;
	
	@Override
	public BigDecimal computeBalanceAmount(TransactionType transactionType, BigDecimal balance, BigDecimal amount) throws InvalidTransactionAmountException {
		if (TransactionType.D.equals(transactionType)) {
			balance = balance.add(amount);
		}
		else { 
			if (balance.compareTo(amount) < 0) {
				logger.debug("Exception lors du calcul de la nouvele balance du compte. Le montant de la transaction est superieur a la balance actuelle.");
				throw new InvalidTransactionAmountException(messageService.getMessage("transaction.amount.not.greater.account.balance", new String[] {String.valueOf(balance)}));
			}

			balance = balance.subtract(amount);
		}
		
		return balance;
	}

}
