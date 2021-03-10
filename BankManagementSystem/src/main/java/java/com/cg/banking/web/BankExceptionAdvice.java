package com.cg.banking.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.banking.dto.AccountMessage;
import com.cg.banking.exceptions.AccountException;
import com.cg.banking.exceptions.BalanceException;
import com.cg.banking.exceptions.CustomerException;
import com.cg.banking.exceptions.LoanRequestException;
import com.cg.banking.exceptions.TransactionException;
import com.cg.banking.util.AccountConstants;

@RestControllerAdvice
public class BankExceptionAdvice {

	Logger logger = LoggerFactory.getLogger(BankExceptionAdvice.class);
	
	@ExceptionHandler(value= {BalanceException.class})
	@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason=AccountConstants.INSUFFICIENT_BALANCE)
	public void handleException(Exception ex) {
		logger.error(ex.getMessage());
	}
	
	@ExceptionHandler(value= {AccountException.class, CustomerException.class})
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	public AccountMessage handleException2(Exception ex){
		logger.error(ex.getMessage());
		return new AccountMessage(ex.getMessage());
	}
	
	@ExceptionHandler(value= {DataIntegrityViolationException.class})
	@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason=AccountConstants.ID_ALREADY_EXISTS)
	public void handleException6(Exception ex){
		logger.error(ex.getMessage());
		
	}
	
	@ExceptionHandler(value= {TransactionException.class, LoanRequestException.class})
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public AccountMessage handleException5(Exception ex){
		logger.error(ex.getMessage());
		return new AccountMessage(ex.getMessage());
	}
}
