package com.cg.banking.service;

import java.sql.Date;
import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.banking.exceptions.AccountException;
import com.cg.banking.exceptions.BalanceException;
import com.cg.banking.exceptions.TransactionException;
import com.cg.banking.util.AccountConstants;
import com.cg.banking.dao.BankDao;
import com.cg.banking.entity.AccTransaction;
import com.cg.banking.entity.Account;
import com.cg.banking.entity.LDTConverter;


@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private BankDao dao;

	//@Value("${noOfTxns}")
	@Value("5")
	private Integer noOfTxns;

	@Override
	@Transactional
	public boolean transferFund(String fromAccountId, String toAccountId, double amount) throws BalanceException, AccountException, TransactionException {
		
		Account fromAccount = dao.viewAccount(fromAccountId);
		Account toAccount = dao.viewAccount(toAccountId);
		if(fromAccount==null)throw new AccountException(AccountConstants.INVALID_ACCOUNT+fromAccountId);
		if(toAccount==null)throw new AccountException(AccountConstants.INVALID_ACCOUNT + toAccountId);
		if(!fromAccount.getAccountStatus().equals(AccountConstants.ACTIVE))
			throw new TransactionException(AccountConstants.IN_ACTIVE);
		if(!toAccount.getAccountStatus().equals(AccountConstants.ACTIVE))
			throw new TransactionException(AccountConstants.IN_ACTIVE);
		if (fromAccount.getAccountBalance() < amount)throw new BalanceException();
		fromAccount.setAccountBalance(fromAccount.getAccountBalance() - amount);
		toAccount.setAccountBalance(toAccount.getAccountBalance() + amount);
		dao.editAccount(fromAccount);
		dao.editAccount(toAccount);
		addTransaction(AccountConstants.DEBIT, amount, AccountConstants.TRANSFER_TO+toAccountId, fromAccount);
		addTransaction(AccountConstants.CREDIT, amount,AccountConstants.TRANSFER_TO+fromAccountId, toAccount);
		return true;
	}

	@Override
	public List<AccTransaction> getTransactions(String accId) throws TransactionException, AccountException  {
		Account account = dao.viewAccount(accId);
		if (!account.getAccountStatus().equals(AccountConstants.ACTIVE))
			throw new TransactionException(AccountConstants.IN_ACTIVE);
		List<AccTransaction> txList = dao.getTransactions(accId,noOfTxns);
		if(txList.isEmpty())
			throw new AccountException(AccountConstants.NO_TRANSACTION);
		txList.sort((tx1, tx2)-> tx2.getTransDate().compareTo(tx1.getTransDate()));
		return txList;
	}

	@Override
	public List<AccTransaction>  getTransactions(String accId, LocalDate fromDt, LocalDate toDt) throws TransactionException, AccountException {
		Account account = dao.viewAccount(accId);
		if (account.getAccountStatus().equals(AccountConstants.ACTIVE)) {
		List<AccTransaction> txLst = dao.getTransactions(accId,fromDt, toDt);
		if(txLst.isEmpty()) throw new AccountException(AccountConstants.NO_TRANSACTION);
		txLst.sort((tx1, tx2)-> tx2.getTransDate().compareTo(tx1.getTransDate()));
		return txLst;
		}
		throw new TransactionException(AccountConstants.IN_ACTIVE);
	}
	
	public void addTransaction(String txType,double amt,String txnDesc,Account account) {
		AccTransaction tx1=new AccTransaction();
		tx1.setAccount(account);
		tx1.setTransAmount(amt);
		tx1.setTransType(txType);
		tx1.setTransDate(java.time.LocalDate.now());
		tx1.setTransDescription(txnDesc);
		dao.addTxn(tx1);
	}

}
