package com.cg.banking.exceptions;

public class ErrorInfo {
	private String message;
	private String error="Bad Request";
	private int status=400;
	
	public ErrorInfo() {
		
	}
	
	public ErrorInfo(String msg) {
		super();
		this.message=msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
