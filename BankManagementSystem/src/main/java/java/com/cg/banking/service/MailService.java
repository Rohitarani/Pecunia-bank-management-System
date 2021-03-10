package com.cg.banking.service;

import com.cg.banking.exceptions.InvalidMailException;

public interface MailService {
	
	public String sendMail(String toEmail,String customerId,String password) throws InvalidMailException;

}
