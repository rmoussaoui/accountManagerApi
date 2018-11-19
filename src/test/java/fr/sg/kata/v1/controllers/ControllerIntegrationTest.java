package fr.sg.kata.v1.controllers;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sg.kata.v1.data.TransactionData;
import fr.sg.kata.v1.data.TransactionRequestData;
import fr.sg.kata.v1.models.TransactionType;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ControllerIntegrationTest {
	
	@LocalServerPort
    private int serverPort;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	private String url;
	
	@Before
	public void setup() {
		url = "http://localhost:" + serverPort + "/account-manager/v1/accounts/{accountId}/transactions";
	}
	
	@Test
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/cleantransactions.sql")
	public void testDoTransaction() {
		
		Map<String, String> map = new HashMap<String, String>();
        map.put("accountId", "1111");
		
		TransactionRequestData transactionRequestData = new TransactionRequestData();
		transactionRequestData.setAmount(new BigDecimal("500"));
		transactionRequestData.setTransactionType(TransactionType.D);
		ResponseEntity<TransactionData> response = restTemplate.postForEntity(url, transactionRequestData, TransactionData.class, map);
		
		assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
		HttpHeaders headers = response.getHeaders();
		TransactionData createdTransaction = response.getBody();
		
		assertTrue("1111".equals(createdTransaction.getAccountId()));
		assertTrue(transactionRequestData.getAmount().equals(createdTransaction.getAmount()));
		assertTrue(transactionRequestData.getTransactionType().equals(createdTransaction.getTransactionType()));
		assertTrue(headers.containsKey(HttpHeaders.LOCATION));
	}
	
	@Test
	@SqlGroup({
	    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/transactions.sql"),
	    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/cleantransactions.sql")
	})
	public void testAccountHistoryWithDateRange() {
		Map<String, String> map = new HashMap<String, String>();
        map.put("accountId", "1111");
        url = url.concat("?start=09-07-2018&end=11-08-2018");

		ResponseEntity<Collection> history = restTemplate.getForEntity(url, Collection.class, map);
		
		assertTrue(history.getBody().size() == 2);
		
	}

	@Test
	@SqlGroup({
	    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/transactions.sql"),
	    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/cleantransactions.sql")
	})
	public void testAccountHistoryWithStartDate() {
		Map<String, String> map = new HashMap<String, String>();
        map.put("accountId", "1111");
        url = url.concat("?start=09-06-2018");

		ResponseEntity<Collection> history = restTemplate.getForEntity(url, Collection.class, map);
		
		assertTrue(history.getBody().size() == 6);
		
	}

	@Test
	@SqlGroup({
	    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:transactions.sql"),
	    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleantransactions.sql")
	})
	public void testAccountHistoryWithEndDate() {
		Map<String, String> map = new HashMap<String, String>();
        map.put("accountId", "1111");
        url = url.concat("?end=09-08-2018");

		ResponseEntity<Collection> history = restTemplate.getForEntity(url, Collection.class, map);
		
		assertTrue(history.getBody().size() == 4);
		
	}

	@Test
	@SqlGroup({
	    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/transactions.sql"),
	    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/cleantransactions.sql")
	})
	public void testAccountHistoryWithoutDateRange() {
		Map<String, String> map = new HashMap<String, String>();
        map.put("accountId", "1111");

		ResponseEntity<Collection> history = restTemplate.getForEntity(url, Collection.class, map);
		
		assertTrue(history.getBody().size() == 8);
		
	}

}
