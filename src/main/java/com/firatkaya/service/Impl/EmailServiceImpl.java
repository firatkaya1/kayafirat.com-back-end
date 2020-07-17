package com.firatkaya.service.Impl;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.firatkaya.service.EmailService;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class EmailServiceImpl implements EmailService {

	private static final String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void sendVerificationEmail(String emailAddress,String id) throws MessagingException {
		String token = createToken(emailAddress,id);
		String verificationAddress = "localhost:4200/confirm/"+token;
		String messageText= "Dear " + emailAddress+"\n\n"
	            +"please verifiy your account.\n\n"
	            +"<html><body> "
	            +"<a href='"+verificationAddress+"'>"
	            +"Click Link.<br>"
	            +"</a></body></html>"
	            + "Link : "+verificationAddress;
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		helper.setFrom("fratkaya@mail.com");
		helper.setSubject("Confirm Account");
		helper.setTo(emailAddress);
		helper.setText(messageText,true);
	        try{
	            this.mailSender.send(message);
	        }
	        catch (MailException ex) {
	            System.err.println(ex.getMessage());
	        }
	}
	
	@Override
	public String createToken(String email,String id) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        
        JwtBuilder builder = Jwts.builder().setId(email)
                .setIssuedAt(now)
                .setSubject(id)
                .setIssuer("kayafirat.com")
                .setExpiration(new Date(nowMillis + (600000 * 3)))
                .signWith(signatureAlgorithm, signingKey);
        
		     return builder.compact();   	    
	}

}
