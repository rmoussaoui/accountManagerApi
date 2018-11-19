package fr.sg.kata.v1.services;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sg.kata.v1.exception.AccountNotFoundException;
import fr.sg.kata.v1.models.Account;
import fr.sg.kata.v1.services.IAccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceIntegrationTest {
	
	private static final String ACCOUNT_ID = "1111";
	
	@Autowired
	private IAccountService accountService;
	
	@Test(expected = AccountNotFoundException.class)
	public void testAccountNotFound() throws AccountNotFoundException {
		accountService.getAccountById("dummyaccount");
	}
	
	public void testAccountFound() throws AccountNotFoundException {
		Account account = accountService.getAccountById(ACCOUNT_ID);
		assertTrue(account.getAccountId() == ACCOUNT_ID);
	}
	
}
