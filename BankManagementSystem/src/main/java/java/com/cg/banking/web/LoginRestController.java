package com.cg.banking.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.banking.entity.Customer;
import com.cg.banking.service.AccountLoginService;

import com.cg.banking.exceptions.LoginException;


@RestController
public class LoginRestController {

	@Autowired
	private AccountLoginService ser;

	@Autowired
	@Qualifier("authenticatemap")
	private Map<String, Customer> authMap;
	
	@CrossOrigin
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String getLogin(@RequestParam("userid") String userId, @RequestParam("password") String password	) throws LoginException {
		Customer user = ser.doLogin(userId, password);
        
		String token = ser.encryptUser(user);
		authMap.put(token, user);
		return token;
	}

	@CrossOrigin
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		String token=req.getHeader("userId");
		authMap.remove(token);
		return "logged out";
	}

}
