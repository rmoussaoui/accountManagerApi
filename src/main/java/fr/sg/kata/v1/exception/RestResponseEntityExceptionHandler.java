package fr.sg.kata.v1.exception;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.sg.kata.v1.services.IMessageService;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler  {
	
	@Autowired
	private IMessageService messageService;

	  @ExceptionHandler(AccountNotFoundException.class)
	  public final ResponseEntity<ApiError> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) {
	    ApiError errorDetails = new ApiError(LocalDate.now(), ex.getMessage(), request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	  }
	
	  @ExceptionHandler(InvalidTransactionAmountException.class)
	  public final ResponseEntity<ApiError> handleInvalidTransactionAmountException(InvalidTransactionAmountException ex, WebRequest request) {
	    ApiError errorDetails = new ApiError(LocalDate.now(), ex.getMessage(), request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
	  }

	  @ExceptionHandler(AccountOperationNotAllowedException.class)
	  public final ResponseEntity<Object> handleAccountOperationNotAllowedException(
			  AccountOperationNotAllowedException ex, WebRequest request) {

		    ApiError errorDetails = new ApiError(LocalDate.now(), ex.getMessage(), request.getDescription(false));
		    return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
	  }

	  @Override
	  public final ResponseEntity<Object> handleMethodArgumentNotValid(
			  MethodArgumentNotValidException ex, HttpHeaders headers, 
			  HttpStatus status, WebRequest request) {

		  BindingResult result = ex.getBindingResult();
		List<ApiSubError> apiSubErrors = new ArrayList<>();
		result.getFieldErrors().forEach(fieldError -> apiSubErrors.add(new ApiValidationError(fieldError.getField(), fieldError.getDefaultMessage())));
		String validationMessage = messageService.getMessage("invalid.parameters.exception");
	    ApiError errorDetails = new ApiError(LocalDate.now(), validationMessage, apiSubErrors, request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
	  }
	
	  

	
}
