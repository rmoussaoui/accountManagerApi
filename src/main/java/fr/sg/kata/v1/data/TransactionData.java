package fr.sg.kata.v1.data;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import fr.sg.kata.v1.models.TransactionType;
import lombok.Data;

@Data
public class TransactionData {
	
	private Long transactionId;
	private String accountId;
	private String lastname;
	private String firstname;
	private BigDecimal amount;
	private BigDecimal accountBalance;
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate transactionDate;
	private TransactionType transactionType;
	private String details;
}
