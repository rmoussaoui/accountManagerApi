package fr.sg.kata.v1.controllers;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import fr.sg.kata.v1.converters.IConverter;
import fr.sg.kata.v1.data.TransactionData;
import fr.sg.kata.v1.data.TransactionRequestData;
import fr.sg.kata.v1.exception.AccountNotFoundException;
import fr.sg.kata.v1.exception.InvalidTransactionAmountException;
import fr.sg.kata.v1.exception.InvalidTransactionRequestDataException;
import fr.sg.kata.v1.models.Transaction;
import fr.sg.kata.v1.services.IAccountService;
import fr.sg.kata.v1.services.ITransactionService;

@RestController
@RequestMapping("account-manager/v1/accounts")
public class TransactionController {
	
	
	//==========  Converters
	
	@Autowired
	private IConverter<Transaction,TransactionData> transactionConverter;
	
	//========== Services
	
	@Autowired
	private ITransactionService transactionService;
	
	@Autowired
	private IAccountService accountService;
	

	@PostMapping("{accountId}/transactions")
	public ResponseEntity<TransactionData> doTransaction(
			@PathVariable(value="accountId", required=true) String accountId,
			@Valid @RequestBody TransactionRequestData transactionRequestData,
			final UriComponentsBuilder uriComponentsBuilder) throws AccountNotFoundException, InvalidTransactionAmountException, InvalidTransactionRequestDataException, NoSuchMethodException, SecurityException, MethodArgumentNotValidException {
		
		TransactionData savedTransaction = transactionConverter.convert(transactionService.doTransaction(transactionRequestData, accountId));
		
		final HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(uriComponentsBuilder
	    		.path("/" + accountId + "/transactions/{id}")
	    		.buildAndExpand(savedTransaction
	    		.getTransactionId()).toUri());
	 
	    return new ResponseEntity<TransactionData>(savedTransaction, headers, HttpStatus.CREATED);
	}
	
	@GetMapping(value="{accountId}/transactions")
	public ResponseEntity<Collection<TransactionData>> getTransactionsLog(
			@PathVariable(value="accountId", required=true) String accountId,
			@RequestParam(value="start",required=false) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate startDate, 
			@RequestParam(value="end",required=false) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate endDate) throws AccountNotFoundException {
		
		Collection<TransactionData> ctransactionDto = transactionConverter.convertAll(accountService.getAccountHistory(accountId, startDate, endDate));
		
		return new ResponseEntity<Collection<TransactionData>>(ctransactionDto, HttpStatus.OK);
		
	}
	
}
