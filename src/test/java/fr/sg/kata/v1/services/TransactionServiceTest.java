package fr.sg.kata.v1.services;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import fr.sg.kata.utils.TestUtils;
import fr.sg.kata.v1.data.TransactionRequestData;
import fr.sg.kata.v1.exception.AccountNotFoundException;
import fr.sg.kata.v1.exception.InvalidTransactionAmountException;
import fr.sg.kata.v1.models.Account;
import fr.sg.kata.v1.models.Transaction;
import fr.sg.kata.v1.models.TransactionType;
import fr.sg.kata.v1.repository.IAccountRepository;
import fr.sg.kata.v1.repository.ITransactionRepository;
import fr.sg.kata.v1.services.impl.TransactionServiceImpl;

public class TransactionServiceTest {
	
	private static final String ACCOUNT_ID = "1111";
	
	@InjectMocks
	private TransactionServiceImpl transactionService;
	
	@Mock
	private IAccountService accountService;

	@Mock
	private IAccountRepository accountRepository;

	@Mock
	private ITransactionRepository transactionRepository;
	
	@Mock
	private IAmountCalculationStrategy amountCalculationStrategy;
	
	@Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test(expected=AccountNotFoundException.class)
	public void testShouldRaiseAccountException() throws AccountNotFoundException, InvalidTransactionAmountException {
		Mockito.when(accountService.getAccountById(ACCOUNT_ID)).thenThrow(AccountNotFoundException.class);
		TransactionRequestData transactionRequestData = TestUtils.createTransactionRequestData(new BigDecimal("100"), "transaction details", TransactionType.D);
		
		transactionService.doTransaction(transactionRequestData, ACCOUNT_ID);
	}
	
	@Test(expected=InvalidTransactionAmountException.class)
	public void shouldRaiseAnInvalidTransactionAmountExceptionTest() throws InvalidTransactionAmountException, AccountNotFoundException {
		Mockito.when(amountCalculationStrategy.computeBalanceAmount(Mockito.any(TransactionType.class), Mockito.any(), Mockito.any())).thenThrow(InvalidTransactionAmountException.class);
		Mockito.when(accountService.getAccountById(ACCOUNT_ID)).thenReturn(TestUtils.createAccount(ACCOUNT_ID, new BigDecimal("30000")));

		TransactionRequestData transactionRequestData = TestUtils.createTransactionRequestData(new BigDecimal("100"), "transaction details", TransactionType.D);
		
		transactionService.doTransaction(transactionRequestData, ACCOUNT_ID);
		
	}
	
	@Test
	public void shouldReturnRightTransaction() throws AccountNotFoundException, InvalidTransactionAmountException {
		Account account = TestUtils.createAccount(ACCOUNT_ID, new BigDecimal("30000"));
		TransactionRequestData transactionRequestData = TestUtils.createTransactionRequestData(new BigDecimal("3000"), "transaction details", TransactionType.D);
		Transaction transaction = TestUtils.createTransaction(Long.valueOf(123), TransactionType.D, account
				, new BigDecimal("1000"), new BigDecimal("33000"), LocalDate.now().minusDays(10));
		
		Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);
		Mockito.when(accountService.getAccountById(ACCOUNT_ID)).thenReturn(account);
		Mockito.when(amountCalculationStrategy.computeBalanceAmount(Mockito.any(TransactionType.class), Mockito.any(), Mockito.any())).thenReturn(transaction.getAccountBalance());
		
		Transaction savedTransaction = transactionService.doTransaction(transactionRequestData, ACCOUNT_ID);
		
		assertTrue(savedTransaction.equals(transaction));
 
	}
}
