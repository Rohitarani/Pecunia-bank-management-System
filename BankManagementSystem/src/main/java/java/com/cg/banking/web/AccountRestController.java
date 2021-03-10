package com.cg.banking.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.banking.exceptions.AccountException;
import com.cg.banking.exceptions.CustomerException;
import com.cg.banking.service.AccountService;
import com.cg.banking.util.AccountConstants;
import com.cg.banking.dto.AccountForm;
import com.cg.banking.dto.AccountMessage;
import com.cg.banking.entity.Account;

@RestController
public class AccountRestController {

	@Autowired
	public AccountService service;
	
	@CrossOrigin
	@PostMapping("/addaccount")
	public AccountMessage addAccount(@RequestBody AccountForm accountForm) throws AccountException, CustomerException {
		try {
			String accId=service.addAccount(accountForm);
			return new AccountMessage(AccountConstants.ACCOUNT_CREATED + AccountConstants.GENERATED_ACCOUNT+accId);

		} catch (DataIntegrityViolationException ex) {
			throw new AccountException(AccountConstants.ID_ALREADY_EXISTS);
		}
	}

	@CrossOrigin
	@PutMapping("/editaccount")
	public AccountMessage editAccount(@RequestBody Account account) {
		service.editAccount(account);
		AccountMessage msg = new AccountMessage(AccountConstants.ACCOUNT_EDITED);
		return msg;
	}
	
	@CrossOrigin
	@GetMapping("/viewaccountbyaccid/{accid}")
	public Account viewAccount(@PathVariable("accid") String accId) throws AccountException  {
		Account account = service.viewAccount(accId);
		
		return account;
	}
	
	@CrossOrigin
	@GetMapping("/viewaccountsbycustid/{custid}")
	public List<Account> viewAccounts(@PathVariable("custid") String custId) throws CustomerException {
		return service.viewAccounts(custId);
	}

}
