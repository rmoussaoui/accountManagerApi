package fr.sg.kata.v1.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sg.kata.v1.exception.AccountNotFoundException;
import fr.sg.kata.v1.models.Account;
import fr.sg.kata.v1.models.Transaction;
import fr.sg.kata.v1.models.TransactionType;
import fr.sg.kata.v1.services.IAccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
@SqlGroup({
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:transactions.sql"),
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleantransactions.sql")
})
public class TransactionRepositoryTest {
	
	private final static String ACCOUNT_ID= "1111";
	private final static LocalDate START_DATE = LocalDate.parse("2018-06-09");
	private final static LocalDate END_DATE = LocalDate.parse("2018-08-09");
	private final static LocalDate FUTURE_DATE = LocalDate.parse("2020-06-09");

	@Autowired
	private ITransactionRepository transactionRepository;
	
	@Test
	public void testAccountHistoryWithStartDate() {
		
		List<Transaction> history = transactionRepository.getAccountHistory(ACCOUNT_ID, START_DATE, null);
		
		assertTrue(history.size() == 6);
		
	}
	
	@Test
	public void testAccountHistoryWithEndDate() {
		List<Transaction> history = transactionRepository.getAccountHistory(ACCOUNT_ID, null,  END_DATE);
		
		assertTrue(history.size() == 4);
	}
	
	@Test
	public void testAccountHistoryWithStartDateAndEndDate() {
		
		List<Transaction> history = transactionRepository.getAccountHistory(ACCOUNT_ID, START_DATE, END_DATE);
		
		assertTrue(history.size() == 2);
		
	}
	
	@Test
	public void testAccountHistoryNoHistory() {
		
		List<Transaction> history = transactionRepository.getAccountHistory	(ACCOUNT_ID, FUTURE_DATE, FUTURE_DATE);
		
		assertTrue(history.size() == 0);
		
	}

}
