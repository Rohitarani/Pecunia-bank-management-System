package com.cg.banking;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cg.banking.entity.Customer;
import com.cg.banking.util.MailConstants;


@SpringBootApplication
public class SpringBootProject1Application {
	
	//@Autowired
	//private Environment environmet;
	
	@Bean(name="authenticatemap")
	public Map<String,Customer> getAuthentication(){
		return new HashMap<>();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProject1Application.class, args);
	}
	
	@Bean(name="mailprops")
	public Properties getMailProperties() {
		Properties props=System.getProperties();
		
		props.setProperty(MailConstants.HOST, "smtp.gmail.com");
		props.setProperty(MailConstants.PORT, "587");
		props.setProperty(MailConstants.AUTH, "true");
		props.setProperty(MailConstants.STARTTLS, "true");
		
		return props;
		
	}

}
