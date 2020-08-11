package com.firatkaya.service;

import java.util.HashMap;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	void sendVerificationEmail(HashMap<String, String> request) throws MessagingException;
	
	void sendResetPasswordEmail(HashMap<String, String>  request) throws MessagingException;
	
	String createToken(String email,String id,String code);
	
	void sendSuccessResetPassword(String emailAddress,String ipAddress,String UserAgent) throws MessagingException;
	
    void sendSuccessVerification(String emailAddress) throws MessagingException; 
	
}
