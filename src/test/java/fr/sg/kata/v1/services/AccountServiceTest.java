package fr.sg.kata.v1.services;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import fr.sg.kata.v1.exception.AccountNotFoundException;
import fr.sg.kata.v1.models.Account;
import fr.sg.kata.v1.models.Client;
import fr.sg.kata.v1.models.Transaction;
import fr.sg.kata.v1.models.TransactionType;
import fr.sg.kata.v1.repository.IAccountRepository;
import fr.sg.kata.v1.repository.ITransactionRepository;
import fr.sg.kata.v1.services.impl.AccountServiceImpl;

public class AccountServiceTest {
	
	private static final String ACCOUNT_ID = "1234";
	
	@InjectMocks
    private AccountServiceImpl accountService;
	
	@Mock
	private ITransactionRepository transactionRepository;
	
	@Mock
	private IAccountRepository accountRepository;
	
	@Mock
	private IMessageService messageService;
	
	@Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test(expected=AccountNotFoundException.class)
	public void shouldRaiseException() throws AccountNotFoundException {
		Mockito.when(accountRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		accountService.getAccountById(ACCOUNT_ID);
	}

	@Test(expected=AccountNotFoundException.class)
	public void shouldRaiseException2() throws AccountNotFoundException {
		Mockito.when(accountRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		accountService.getAccountHistory(ACCOUNT_ID, null, null);
	}

	@Test
	public void shouldReturnRightAccount() throws AccountNotFoundException {
		
		Account account = new Account(ACCOUNT_ID, new BigDecimal("1000"), new Client());
		
		Mockito.when(accountRepository.findById(Mockito.anyString())).thenReturn(Optional.of(account));
		Account returnedAccount = accountService.getAccountById(ACCOUNT_ID);
		assertTrue(account.equals(returnedAccount));
	}
	
	@Test
	public void shouldReturnRightAccountHistory() throws AccountNotFoundException {
		Account account1 = new Account(ACCOUNT_ID, new BigDecimal("1000"), new Client());
				
		Transaction transaction1 = new Transaction(Long.valueOf(123), new BigDecimal("100"), new BigDecimal("1000")
				, TransactionType.D, LocalDate.now().minusDays(10), "Details about transaction 123", account1);
		
		Transaction transaction2 = new Transaction(Long.valueOf(456), new BigDecimal("120"), new BigDecimal("2500")
				, TransactionType.W, LocalDate.now().minusDays(5), "Details about transaction 456", account1);
		
		List<Transaction> transactionList = Arrays.asList(transaction1, transaction2);
		
		Mockito.when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account1));

		Mockito.when(transactionRepository.getAccountHistory(Mockito.anyString(), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
		.thenReturn(transactionList);
		
		List<Transaction> history = accountService.getAccountHistory(ACCOUNT_ID, LocalDate.now().minusDays(10), LocalDate.now());
		assertTrue(history.size() == 2);
		assertTrue(history.get(0).equals(transaction1));
		assertTrue(history.get(1).equals(transaction2));
		
	}
}
