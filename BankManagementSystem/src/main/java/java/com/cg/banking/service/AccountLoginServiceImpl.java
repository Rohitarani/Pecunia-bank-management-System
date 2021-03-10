package com.cg.banking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.banking.dao.BankDao;
import com.cg.banking.entity.Customer;
import com.cg.banking.exceptions.LoginException;

@Service
public class AccountLoginServiceImpl implements AccountLoginService{

	@Autowired
	private BankDao dao;
	
	
	
		
	Logger logger = LoggerFactory.getLogger(AccountLoginServiceImpl.class);
	
	@Override
	public Customer doLogin(String userId, String password)throws LoginException {
		Customer user = dao.viewCustomer(userId);
		logger.debug("doing login process");
		
		if (user != null && user.getPassword().contentEquals(password)){
			logger.info("User Authenticated for " + userId);
			return user;
		}
		throw new LoginException("You are not authenticated and authorized, Please Login");
	}

	@Override
	public String encryptUser(Customer user) {
		return encryptString(user.getCustomerId())+"-" +encryptString(user.getCustomerName())+"-"
		      +encryptString(user.getRole());
	}
	
	public String encryptString(String str) {
		char[] arr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		int ch ;
		for (int idx=0; idx < arr.length; ++idx) {
			ch = arr[idx]+3;
			sb.append((char)ch);
		}
		return sb.toString();
	}

	public String decryptString(String str) {
		char[] arr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		int ch ;
		for (int idx=0; idx < arr.length; ++idx) {
			ch = arr[idx]-3;
			sb.append((char)ch);
		}
		return sb.toString();
	}
}
