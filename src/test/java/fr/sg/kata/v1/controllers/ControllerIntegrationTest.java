package fr.sg.kata.v1.controllers;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import fr.sg.kata.v1.data.TransactionData;
import fr.sg.kata.v1.data.TransactionRequestData;
import fr.sg.kata.v1.models.TransactionType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Configuration
//@Ignore
public class ControllerIntegrationTest {
	private static final String ACCOUNT_ID = "1111";
	
	@LocalServerPort
    private int serverPort;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	private String url;
	
	private String jwtToken;
	
	@Before
	public void setup() throws JSONException {
		url = "http://localhost:" + serverPort + "/account-manager/v1/accounts/{accountId}/transactions";
		jwtToken = createJwtToken();
	}
	
	private String createJwtToken() throws JSONException {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();

		map.add("grant_type", "password");
		map.add("username", "john.doe");
		map.add("password", "jwtpass");
		
		ResponseEntity<String> res = restTemplate.withBasicAuth("testjwtclientid", "XY7kmzoNzl100").postForEntity("http://localhost:" + serverPort + "/oauth/token", map, String.class);
		return new JSONObject(res.getBody()).getString("access_token");
	}
	
	
	@Test
	@WithMockUser(username = "john.doe", roles={ "CLIENT" })
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/cleantransactions.sql")
	public void testDoTransaction() throws JSONException {

		TransactionRequestData transactionRequestData = new TransactionRequestData();
		transactionRequestData.setAmount(new BigDecimal("500"));
		transactionRequestData.setTransactionType(TransactionType.D);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.set("Authorization", "Bearer " + jwtToken);
		HttpEntity<TransactionRequestData> entity = new HttpEntity<>(transactionRequestData, requestHeaders);

		Map<String, String> map = new HashMap<String, String>();
        map.put("accountId", ACCOUNT_ID);

		ResponseEntity<TransactionData> response = restTemplate.postForEntity(url, entity, TransactionData.class, map);
		
		assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
		HttpHeaders headers = response.getHeaders();
		TransactionData createdTransaction = response.getBody();
		
		assertTrue(ACCOUNT_ID.equals(createdTransaction.getAccountId()));
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
        map.put("accountId", ACCOUNT_ID);
        url = url.concat("?start=09-07-2018&end=11-08-2018");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.set("Authorization", "Bearer " + jwtToken);
		HttpEntity<Object> entity = new HttpEntity<>(requestHeaders);
		
		ResponseEntity<Collection> history = restTemplate.exchange(url, HttpMethod.GET, entity, Collection.class, map);

		assertTrue(history.getBody().size() == 2);
		
	}

	@Test
	@SqlGroup({
	    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/transactions.sql"),
	    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/cleantransactions.sql")
	})
	public void testAccountHistoryWithStartDate() {
		Map<String, String> map = new HashMap<String, String>();
        map.put("accountId", ACCOUNT_ID);
        url = url.concat("?start=09-06-2018");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.set("Authorization", "Bearer " + jwtToken);
		HttpEntity<Object> entity = new HttpEntity<>(requestHeaders);

		ResponseEntity<Collection> history = restTemplate.exchange(url, HttpMethod.GET, entity, Collection.class, map);

		assertTrue(history.getBody().size() == 6);
		
	}

	@Test
	@SqlGroup({
	    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:transactions.sql"),
	    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleantransactions.sql")
	})
	public void testAccountHistoryWithEndDate() {
		Map<String, String> map = new HashMap<String, String>();
        map.put("accountId", ACCOUNT_ID);
        url = url.concat("?end=09-08-2018");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.set("Authorization", "Bearer " + jwtToken);
		HttpEntity<Object> entity = new HttpEntity<>(requestHeaders);

		ResponseEntity<Collection> history = restTemplate.exchange(url, HttpMethod.GET, entity, Collection.class, map);
		
		assertTrue(history.getBody().size() == 4);
		
	}

	@Test
	@SqlGroup({
	    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/transactions.sql"),
	    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/cleantransactions.sql")
	})
	public void testAccountHistoryWithoutDateRange() {
		Map<String, String> map = new HashMap<String, String>();
        map.put("accountId", ACCOUNT_ID);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.set("Authorization", "Bearer " + jwtToken);
		HttpEntity<Object> entity = new HttpEntity<>(requestHeaders);

		ResponseEntity<Collection> history = restTemplate.exchange(url, HttpMethod.GET, entity, Collection.class, map);
		
		assertTrue(history.getBody().size() == 8);
		
	}
	
	
	@Test
	@SqlGroup({
	    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/transactions.sql"),
	    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/cleantransactions.sql")
	})
	public void testShouldRaiseException() {
		Map<String, String> map = new HashMap<String, String>();
        map.put("accountId", ACCOUNT_ID);
		
		TransactionRequestData transactionRequestData = new TransactionRequestData();
		transactionRequestData.setAmount(null);
		transactionRequestData.setTransactionType(TransactionType.D);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.set("Authorization", "Bearer " + jwtToken);
		HttpEntity<TransactionRequestData> entity = new HttpEntity<>(transactionRequestData, requestHeaders);

		ResponseEntity<Object> response = restTemplate.postForEntity(url, entity, Object.class, map);
		
		assertTrue(response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
		
		
	}

}
