package com.cg.banking.service;

import com.cg.banking.entity.Customer;
import com.cg.banking.exceptions.LoginException;

public interface AccountLoginService {
    public Customer doLogin(String userId, String password)throws LoginException;
    public String encryptUser(Customer user);
    
}
