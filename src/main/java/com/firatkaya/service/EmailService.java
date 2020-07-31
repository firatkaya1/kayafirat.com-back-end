package com.firatkaya.service;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	void sendVerificationEmail(String emailAddress,String id) throws MessagingException;
	
	void sendResetPasswordEmail(String emailAddress,String id) throws MessagingException;
	
	String createToken(String email,String id,String code);
	
	void sendSuccessResetPassword(String emailAddress,String ipAddress,String UserAgent) throws MessagingException;
	
   void sendSuccessVerification(String emailAddress) throws MessagingException; 
	
}
