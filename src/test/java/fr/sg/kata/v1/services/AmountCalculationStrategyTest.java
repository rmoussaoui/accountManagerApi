package fr.sg.kata.v1.services;

import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.sg.kata.v1.exception.InvalidTransactionAmountException;
import fr.sg.kata.v1.models.TransactionType;
import fr.sg.kata.v1.services.impl.AmountCalculationStrategyImpl;

public class AmountCalculationStrategyTest {
	
	@InjectMocks
	private AmountCalculationStrategyImpl amountCalculationStrategy;
	
	@Mock
	private IMessageService messageService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected=InvalidTransactionAmountException.class)
	public void shouldRaiseExcpeion() throws InvalidTransactionAmountException {
		
		amountCalculationStrategy.computeBalanceAmount(TransactionType.W, new BigDecimal("20000"), new BigDecimal("21000"));
		
	}
	
	@Test
	public void shouldReturnRightAmount() throws InvalidTransactionAmountException {
		
		BigDecimal newBalance = amountCalculationStrategy.computeBalanceAmount(TransactionType.W, new BigDecimal("21000"), new BigDecimal("1000"));
		
		assertTrue(newBalance.equals(new BigDecimal("20000")));
		
	}

}
