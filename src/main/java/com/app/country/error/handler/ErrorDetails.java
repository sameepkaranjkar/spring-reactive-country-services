package com.app.country.error.handler;

import java.util.Date;

public class ErrorDetails {

	private Date timeStamp;
	private String errorMessage;
	private String errorInfo;

	public ErrorDetails(Date timeStamp, String errorMessage, String errorInfo) {
		super();
		this.timeStamp = timeStamp;
		this.errorMessage = errorMessage;
		this.errorInfo = errorInfo;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

}
