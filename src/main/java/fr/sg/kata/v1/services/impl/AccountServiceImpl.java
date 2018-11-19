package fr.sg.kata.v1.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.sg.kata.v1.exception.AccountNotFoundException;
import fr.sg.kata.v1.models.Account;
import fr.sg.kata.v1.models.Transaction;
import fr.sg.kata.v1.repository.IAccountRepository;
import fr.sg.kata.v1.repository.ITransactionRepository;
import fr.sg.kata.v1.services.IAccountService;
import fr.sg.kata.v1.services.IMessageService;

@Service
public class AccountServiceImpl implements IAccountService {
	
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private ITransactionRepository transactionRepository;
	
	@Autowired
	private IMessageService messageService;
	
	@Override
	public List<Transaction> getAccountHistory(String accountId, LocalDate startDate, LocalDate endDate) throws AccountNotFoundException {

		Optional<Account> optAccount = accountRepository.findById(accountId);
		if (!optAccount.isPresent()) {
			throw new AccountNotFoundException(messageService.getMessage("account.inexistant", new String[] {accountId}));
		}
		
		return transactionRepository.getAccountHistory(accountId, startDate, endDate);
	}

	@Override
	public Account getAccountById(String accountId) throws AccountNotFoundException {
		Optional<Account> optAccount = accountRepository.findById(accountId);
		if (!optAccount.isPresent()) {
			throw new AccountNotFoundException(messageService.getMessage("account.inexistant", new String[] {accountId}));
		}
		return optAccount.get();
	}

}
