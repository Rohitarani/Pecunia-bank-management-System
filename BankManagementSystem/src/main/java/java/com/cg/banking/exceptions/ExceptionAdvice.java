package com.cg.banking.exceptions;



import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.banking.exceptions.ErrorInfo;
import com.cg.banking.util.AccountConstants;


@RestControllerAdvice
public class ExceptionAdvice {
	
	@ExceptionHandler(value= {DataIntegrityViolationException.class})
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ErrorInfo handlerException1(ConstraintViolationException ex) {
		if(ex.getConstraintName().equals(AccountConstants.aadharConstraint))  //
			return new ErrorInfo("Already registered with this Aadhar Number.");
		
		if(ex.getConstraintName().equals(AccountConstants.panConstraint))
			return new ErrorInfo("Already registered with this Pan Number.");
		
		if(ex.getConstraintName().equals(AccountConstants.contactConstraint))
			return new ErrorInfo("Already registered with this mobile number.");
		
		if(ex.getConstraintName().equals(AccountConstants.emailConstraint))
			return new ErrorInfo("Already registered with this email Id");
		
		else return new ErrorInfo(ex.getMessage());	
	}
	
	@ExceptionHandler(value= {AgeException.class})
	@ResponseStatus(code=HttpStatus.BAD_REQUEST,reason="Minimun age must be 18.")
	public void handlerException2(Exception ex) {
		
	}
	
	@ExceptionHandler(value= {InvalidMailException.class})
	@ResponseStatus(code=HttpStatus.BAD_REQUEST,reason="Invalid email.")
	public void handlerException3(Exception ex) {
		
	}
	
	

	

}
