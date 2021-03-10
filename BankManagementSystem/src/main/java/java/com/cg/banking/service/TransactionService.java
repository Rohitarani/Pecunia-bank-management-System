package com.cg.banking.service;

import java.time.LocalDate;
import java.util.List;

import com.cg.banking.entity.AccTransaction;
import com.cg.banking.exceptions.AccountException;
import com.cg.banking.exceptions.BalanceException;
import com.cg.banking.exceptions.TransactionException;

public interface TransactionService {
	public boolean transferFund(String from,String to,double amount) throws BalanceException, AccountException, TransactionException;
	public List<AccTransaction> getTransactions(String accId)throws TransactionException, AccountException;
	public List<AccTransaction> getTransactions(String accId, LocalDate fromdt, LocalDate todt)throws TransactionException, AccountException;
}
