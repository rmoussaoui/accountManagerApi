package fr.sg.kata.v1.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.sg.kata.v1.data.TransactionRequestData;
import fr.sg.kata.v1.exception.AccountNotFoundException;
import fr.sg.kata.v1.exception.InvalidTransactionAmountException;
import fr.sg.kata.v1.models.Account;
import fr.sg.kata.v1.models.Transaction;
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
	
	@Value("${theValue}")
	private String configuredValue;
	
	@Override
	public Transaction doTransaction(TransactionRequestData transactionRequestData, String accountId) throws AccountNotFoundException, InvalidTransactionAmountException {
		
		Account account = accountService.getAccountById(accountId);
		
		BigDecimal balance = amountCalculationStrategy.computeBalanceAmount(transactionRequestData.getTransactionType(), account.getBalance(), transactionRequestData.getAmount());
		
		Transaction transaction = new Transaction(null, transactionRequestData.getAmount(), balance, transactionRequestData.getTransactionType()
				, LocalDate.now(), transactionRequestData.getDetails(), account);
		
		account.setBalance(balance);
		accountRepository.save(account);
		
		return transactionRepository.save(transaction);
	}

	@Override
	public String getConfigValue() {
		
		return configuredValue;
	}
	
}
