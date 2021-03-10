package com.cg.banking.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cg.banking.dao.BankDao;
import com.cg.banking.dto.AccountForm;
import com.cg.banking.entity.AccTransaction;
import com.cg.banking.entity.Account;
import com.cg.banking.entity.Customer;
import com.cg.banking.exceptions.AccountException;
import com.cg.banking.exceptions.CustomerException;
import com.cg.banking.util.AccountConstants;

@Service("accountser")
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired
	private BankDao dao;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public String addAccount(AccountForm accountForm) throws CustomerException {
		Account account = new Account();
		Customer customer = dao.viewCustomer(accountForm.getCustomerID());
		if (customer == null)
			throw new CustomerException(AccountConstants.CUSTOMER_NOT_FOUND);
		LocalDateTime now = LocalDateTime.now();
		String id = accountForm.getAccountName()+accountForm.getCustomerID();
		account.setAccountId(id);
		account.setAccountBalance(accountForm.getBalance());
		account.setAccountName(accountForm.getAccountName());
		account.setAccountStatus(AccountConstants.ACTIVE);
		account.setLastUpdated(LocalDate.now());
		account.setCustomer(customer);
		dao.addAccount(account);
		
		Account acc=dao.viewAccount(id);
		AccTransaction tx = new AccTransaction();
		tx.setTransAmount(accountForm.getBalance());
		tx.setAccount(acc);
		tx.setTransDate(LocalDate.now());
		//if(accountForm.getAccountName().contains(AccountConstants.LOAN))
			//tx.setTransDescription(AccountConstants.LOAN_DESC);
		//else
		tx.setTransDescription(AccountConstants.OPEN_DESC);
		tx.setTransType(AccountConstants.CREDIT);
		dao.addTxn(tx);
		return id;
	}

	@Override
	public boolean editAccount(Account account) {
		return dao.editAccount(account);
	}

	@Override
	public List<Account> viewAccounts(String custId) throws CustomerException {
		List<Account> accList = dao.viewAccounts(custId);
		if(accList.isEmpty())
			throw new CustomerException(AccountConstants.INVALID_CUSTOMER);
		accList.sort((acc1, acc2)-> acc1.getAccountName().compareTo(acc2.getAccountName()));
		return accList;
	}

	@Override
	public Account viewAccount(String accId) throws AccountException {
		Account account = dao.viewAccount(accId);
		if(account == null)
			throw new AccountException(AccountConstants.INVALID_ACCOUNT);
		return account;
	}

}
