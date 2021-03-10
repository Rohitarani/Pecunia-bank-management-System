package com.cg.banking.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.banking.dao.BankDao;
import com.cg.banking.dto.CustomerForm;
import com.cg.banking.entity.Customer;
import com.cg.banking.exceptions.AgeException;
import com.cg.banking.exceptions.CustomerException;
import com.cg.banking.util.AccountConstants;

@Service("customerser")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private BankDao dao;
	
	@Autowired
	private MailService mailService;
	
	@Override
	public String addCustomer(CustomerForm custForm) throws AgeException {
		Customer cust = new Customer();
		LocalDateTime now = LocalDateTime.now();
		LocalDate now1=LocalDate.now();
		String custID = AccountConstants.EMPTY+ now.getYear()+ now.getMonthValue()+now.getDayOfMonth()+now.getHour()+now.getMinute()+now.getSecond();
		cust.setCustomerId(custID);
		cust.setCustomerName(custForm.getCustomerName());
		cust.setCustomerDob(custForm.getCustomerDob());
		cust.setCustomerAadhar(custForm.getCustomerAadhar());
		cust.setCustomerAddress(custForm.getCustomerAddress());
		cust.setCustomerContact(custForm.getCustomerContact());
		cust.setCustomerPan(custForm.getCustomerPan());
		cust.setPassword(custForm.getPassword());
		cust.setCustomerGender(custForm.getCustomerGender());
		cust.setRole(AccountConstants.ROLE_USER);
		cust.setEmail(custForm.getEmail());
		int age=Math.abs(Period.between(now1, custForm.getCustomerDob()).getYears());
		
		if(age>=18) {
			dao.addCustomer(cust);
			return custID;
		}
		else {
			throw new AgeException();
		}
		
		
	}

	@Override
	public boolean editCustomer(Customer cust) {
		return dao.editCustomer(cust);
	}

	@Override
	public Customer viewCustomer(String custId) throws CustomerException {
		Customer customer = dao.viewCustomer(custId);
		if(customer == null)
			throw new CustomerException(AccountConstants.INVALID_CUSTOMER);
		else{
			return customer;
		}
	}

}
