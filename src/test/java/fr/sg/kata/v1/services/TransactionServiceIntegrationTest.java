package fr.sg.kata.v1.services;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sg.kata.v1.data.TransactionRequestData;
import fr.sg.kata.v1.exception.AccountNotFoundException;
import fr.sg.kata.v1.exception.InvalidTransactionAmountException;
import fr.sg.kata.v1.models.Transaction;
import fr.sg.kata.v1.models.TransactionType;
import fr.sg.kata.v1.services.IAccountService;
import fr.sg.kata.v1.services.ITransactionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceIntegrationTest {
	
	private static final String ACCOUNT_ID = "1111";
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private ITransactionService transactionService;

	@Test
	public void testDoTransaction() throws AccountNotFoundException, InvalidTransactionAmountException {
		 
		 TransactionRequestData transactionRequest = new TransactionRequestData(new BigDecimal("500"), TransactionType.W, "");
		 Transaction savedTransaction = transactionService.doTransaction(transactionRequest, ACCOUNT_ID);
		 assertTrue(savedTransaction.getAmount().equals(new BigDecimal("500")));
		 assertTrue(savedTransaction.getAccountBalance().equals(accountService.getAccountById(ACCOUNT_ID).getBalance()));
	}
	
	@Test(expected = InvalidTransactionAmountException.class)
	public void testTransactionAmountGreaterThanMaxAllowed() throws AccountNotFoundException, InvalidTransactionAmountException {
		 TransactionRequestData transactionRequest = new TransactionRequestData(new BigDecimal("31000"), TransactionType.W, "");
		 transactionService.doTransaction(transactionRequest, ACCOUNT_ID);
	}
	
	
}
