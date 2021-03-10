package com.cg.banking.service;

import com.cg.banking.dto.CustomerForm;
import com.cg.banking.entity.Customer;
import com.cg.banking.exceptions.AgeException;
import com.cg.banking.exceptions.CustomerException;
import com.cg.banking.exceptions.InvalidMailException;

public interface CustomerService {

	public String addCustomer(CustomerForm custForm) throws AgeException, InvalidMailException;
	public boolean editCustomer(Customer cust);
	public Customer viewCustomer(String custId)throws CustomerException;

}
