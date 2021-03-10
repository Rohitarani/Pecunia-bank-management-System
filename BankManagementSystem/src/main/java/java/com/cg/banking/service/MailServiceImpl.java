package com.cg.banking.service;

import com.cg.banking.exceptions.InvalidMailException;
import com.cg.banking.util.MailConstants;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service("mailserv")
public class MailServiceImpl implements MailService{
	
	Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	@Qualifier("mailprops")
	private Properties props;

	@Value("${fromAddress}")
	private String fromAddress;

	@Value("${toAddress}")
	private String reciepentAddress;

	@Value("${pwd}")
	private String password;


	@Override
	public String sendMail(String toEmail,String customerId,String custpassword) throws InvalidMailException {
		System.out.println("Inside Send Mail function()");
		try {
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromAddress, password);
				}
			});

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddress));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject("New Account Created");
			message.setText("You are now our Customer \n Your CustomerId : "+customerId+" \n Password : "+custpassword);
			Transport.send(message);
			logger.info(MailConstants.MAIL_SUCCESS);
			return MailConstants.MAIL_SUCCESS;
		} catch (MessagingException ex) {
			logger.error(ex.getMessage());
			throw new InvalidMailException("Invalid mail");
			//logger.error(ex.getMessage());
			//return MailConstants.MAIL_FAILURE;
		}

	}

}
