package com.cg.banking.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.banking.exceptions.AgeException;
import com.cg.banking.exceptions.CustomerException;
import com.cg.banking.exceptions.InvalidMailException;
import com.cg.banking.service.CustomerService;
import com.cg.banking.service.MailService;
import com.cg.banking.util.AccountConstants;
import com.cg.banking.dto.AccountMessage;
import com.cg.banking.dto.CustomerForm;
import com.cg.banking.entity.Customer;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService service;
	
	@Autowired
	private MailService mailService;
	
	@CrossOrigin
	@PostMapping("/addcustomer")
	public AccountMessage addCustomer(@RequestBody CustomerForm custForm) throws CustomerException, AgeException, InvalidMailException {
		//try {
			String custID = service.addCustomer(custForm);
			String responseFromMail=mailService.sendMail(custForm.getEmail(),custID,custForm.getPassword());
			
		    return new AccountMessage(AccountConstants.CUSTOMER_CREATED+ AccountConstants.GENERATED_CUSTOMER+ custID);
	   // }catch(DataIntegrityViolationException  ex) {  //
		//	throw new CustomerException("Customer ID already exists"); //
		//}
	}
	
	@CrossOrigin
	@GetMapping("/editCustomer")
	public AccountMessage editCustomer(@RequestBody Customer cust) {
		service.editCustomer(cust);
		 return new AccountMessage(AccountConstants.CUSTOMER_EDITED);
	}
	
	@CrossOrigin
	@GetMapping("/viewcustomer/{custid}")
	public Customer viewCustomer(@PathVariable("custid") String custId)throws CustomerException{
		Customer cust=service.viewCustomer(custId);
		return cust;
	}
	
	
}
