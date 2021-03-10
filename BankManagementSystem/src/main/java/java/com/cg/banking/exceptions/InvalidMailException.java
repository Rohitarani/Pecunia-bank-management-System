package com.cg.banking.exceptions;

public class InvalidMailException extends Exception {
	public InvalidMailException() {
		
	}
	
	public InvalidMailException(String msg) {
		super(msg);
	}
}
