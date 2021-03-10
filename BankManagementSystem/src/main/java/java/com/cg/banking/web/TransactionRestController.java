package com.cg.banking.web;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.banking.exceptions.AccountException;
import com.cg.banking.exceptions.BalanceException;
import com.cg.banking.exceptions.TransactionException;
import com.cg.banking.service.TransactionService;
import com.cg.banking.dto.AccReportform;
import com.cg.banking.dto.TransferFundForm;
import com.cg.banking.entity.AccTransaction;

@RestController
public class TransactionRestController {

	@Autowired
	private TransactionService ser;

	@CrossOrigin
	@GetMapping("/getTxn/{accountId}")
	public List<AccTransaction> viewTransaction(@PathVariable("accountId") String accId) throws TransactionException, AccountException {
		List<AccTransaction> txnList = ser.getTransactions(accId);
		
		return txnList;

	}

	@CrossOrigin
	@PostMapping("/getTxn")
	public List<AccTransaction> viewTransaction(@RequestBody AccReportform form) throws TransactionException, AccountException {
		List<AccTransaction> txnList = ser.getTransactions(form.getAccountId(), form.getFromDate(), form.getToDate());
		
		return txnList;

	}

	@CrossOrigin
	@PostMapping("/doTxn")
	public String doTransaction(@RequestBody TransferFundForm form) throws BalanceException, AccountException {
		try {
			if (ser.transferFund(form.getFromAccountId(), form.getToAccountId(), form.getAmt()))
				return "Transaction is completed";
			else
				return "Unable to complete Transaction";
		} catch (DataIntegrityViolationException ex) {
			throw new BalanceException("Insufficient Balance");
		}catch(BalanceException bx) {
			throw new BalanceException("Insufficient Balance");
		}
		catch (Exception ex) {
			throw new AccountException("Account does not exist");
		}

	}
}
