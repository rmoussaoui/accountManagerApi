package fr.sg.kata.v1.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.sg.kata.v1.data.TransactionRequestData;
import fr.sg.kata.v1.exception.AccountNotFoundException;
import fr.sg.kata.v1.exception.InvalidTransactionAmountException;
import fr.sg.kata.v1.models.Account;
import fr.sg.kata.v1.models.Transaction;
import fr.sg.kata.v1.models.TransactionType;
import fr.sg.kata.v1.repository.IAccountRepository;
import fr.sg.kata.v1.repository.ITransactionRepository;
import fr.sg.kata.v1.services.IAccountService;
import fr.sg.kata.v1.services.IAmountCalculationStrategy;
import fr.sg.kata.v1.services.ITransactionService;

@Service
public class TransactionServiceImpl implements ITransactionService  {

	@Autowired
	private ITransactionRepository transactionRepository;
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private IAmountCalculationStrategy amountCalculationStrategy;
	
	@Override
	public Transaction doTransaction(TransactionRequestData transactionRequestData, String accountId) throws AccountNotFoundException, InvalidTransactionAmountException {
		
		Account account = accountService.getAccountById(accountId);
		
		BigDecimal balance = amountCalculationStrategy.computeBalanceAmount(transactionRequestData.getTransactionType(), account.getBalance(), transactionRequestData.getAmount());
		
		Transaction transaction = createTransaction(account, transactionRequestData.getAmount(), transactionRequestData.getTransactionType(),
				LocalDate.now(), balance, transactionRequestData.getDetails()); 
		
		account.setBalance(balance);
		accountRepository.save(account);
		
		return transactionRepository.save(transaction);
	}
	
	
	private Transaction createTransaction(Account account, BigDecimal amount, TransactionType type,
		LocalDate date, BigDecimal balance, String details) {
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setAmount(amount);
		transaction.setTransactionType(type);
		transaction.setTransactionDate(date);
		transaction.setAccountBalance(balance);
		transaction.setDetails(details);
		return transaction;
	}
	
}
