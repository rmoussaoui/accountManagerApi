package fr.sg.kata.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

import fr.sg.kata.v1.data.TransactionRequestData;
import fr.sg.kata.v1.models.Account;
import fr.sg.kata.v1.models.Client;
import fr.sg.kata.v1.models.Transaction;
import fr.sg.kata.v1.models.TransactionType;

public class TestUtils {
	
	public static Account createAccount(String accountId, BigDecimal balance) {
		Account account = new Account();
		account.setAccountId(accountId);
		account.setBalance(balance);
		account.setOwner(new Client());
		return account;
	}

	public static Transaction createTransaction(Long transactionId
			, TransactionType type
			, Account account
			, BigDecimal amount
			, BigDecimal accountBalance
			, LocalDate date) {
		
		Transaction transaction = new Transaction();
		transaction.setTransactionId(transactionId);
		transaction.setAccount(account);
		transaction.setAccountBalance(accountBalance);
		transaction.setAmount(amount);
		transaction.setDetails("Details about transaction " + transactionId.toString());
		transaction.setTransactionDate(date);
		transaction.setTransactionType(type);
		
		return transaction;
	}
	
	public static TransactionRequestData createTransactionRequestData(BigDecimal amount, String details, TransactionType type) {
		TransactionRequestData transactionRequestData = new TransactionRequestData();
		transactionRequestData.setAmount(amount);
		transactionRequestData.setDetails(details);
		transactionRequestData.setTransactionType(type);
		return transactionRequestData;
	}


}
