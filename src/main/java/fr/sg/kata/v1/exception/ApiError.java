package fr.sg.kata.v1.exception;

import java.time.LocalDate;
import java.util.List;

public class ApiError {
	private LocalDate timestamp;
	private String message;
	private List<ApiSubError> subErrors;
	private String url;

	public ApiError(LocalDate timestamp, String message, String url) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.url = url;
	}

	  public ApiError(LocalDate timestamp, String message, List<ApiSubError> subErrors , String url) {
		    this(timestamp, message, url);
		    this.subErrors = subErrors;
		  }

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ApiSubError> getSubErrors() {
		return subErrors;
	}

	public void setSubErrors(List<ApiSubError> subErrors) {
		this.subErrors = subErrors;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
