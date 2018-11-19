package fr.sg.kata.v1.converters;

import org.springframework.stereotype.Component;

import fr.sg.kata.v1.data.TransactionData;
import fr.sg.kata.v1.models.Transaction;

@Component
public class TransactionConverter implements IConverter<Transaction,TransactionData> {

	@Override
	public TransactionData convert(Transaction transaction) {
		TransactionData transactionData = new TransactionData();
		transactionData.setAccountId(transaction.getAccount().getAccountId());
		transactionData.setAmount(transaction.getAmount());
		transactionData.setAccountBalance(transaction.getAccountBalance());
		transactionData.setTransactionType(transaction.getTransactionType());
		transactionData.setTransactionId(transaction.getTransactionId());
		transactionData.setTransactionDate(transaction.getTransactionDate());
		transactionData.setDetails(transaction.getDetails());
		transactionData.setFirstname(transaction.getAccount().getOwner().getFirstname());
		transactionData.setLastname(transaction.getAccount().getOwner().getLastname());
		
		return transactionData;
	}

}
