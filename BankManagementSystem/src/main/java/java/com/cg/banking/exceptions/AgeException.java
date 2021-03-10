package com.cg.banking.exceptions;

public class AgeException extends Exception{
	
	public AgeException() {
		super();
	}
	
	public AgeException(String age) {
		super(age);
	}

}
