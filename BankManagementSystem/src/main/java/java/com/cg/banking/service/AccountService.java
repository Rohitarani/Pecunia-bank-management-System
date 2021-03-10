package com.cg.banking.service;

import java.util.List;

import com.cg.banking.dto.AccountForm;
import com.cg.banking.entity.Account;
import com.cg.banking.exceptions.AccountException;
import com.cg.banking.exceptions.CustomerException;

public interface AccountService {
	
	public String addAccount(AccountForm account)throws CustomerException;
	public boolean editAccount(Account account);
	public List<Account> viewAccounts(String custId)throws CustomerException;
	public Account viewAccount(String accId)throws AccountException;
}
