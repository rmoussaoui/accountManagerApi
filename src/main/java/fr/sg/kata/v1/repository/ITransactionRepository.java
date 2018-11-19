package fr.sg.kata.v1.repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.sg.kata.v1.models.Transaction;

public interface ITransactionRepository extends CrudRepository<Transaction, BigInteger> {
 
	/**
	 * Get a list of transactions for a given account and made during a given date range
	 * @param accountId
	 * @param startDate
	 * @param endDate
	 * @return a transactions list
	 */
	@Query("SELECT t FROM Transaction t WHERE t.account.accountId=:accountId "
			+ "AND ((:startDate is null OR t.transactionDate >= :startDate)) "
			+ "AND ((:endDate is null OR t.transactionDate <= :endDate)) "
			+ "ORDER BY t.transactionDate")
	public List<Transaction> getAccountHistory(@Param("accountId") String accountId, 
			@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate);
	
}
