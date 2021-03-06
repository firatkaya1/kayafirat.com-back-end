package com.kayafirat.service.Impl;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;

import com.kayafirat.validation.constraint.ExistsEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kayafirat.entity.User;
import com.kayafirat.repository.UserRepository;
import com.kayafirat.service.EmailService;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IEmailService implements EmailService {

	private final JavaMailSender mailSender;
	private final UserRepository userRepository;
	private final Environment env;

	@Override
	public void sendVerificationEmail(@ExistsEmail  HashMap<String, String>  request) throws MessagingException {
		User user = userRepository.findByUserEmail(request.get("email"));
		String token = createToken(user.getUserEmail(),user.getUserId(),env.getProperty("mail.verify-code"));
		String verificationAddress = "https://blog.kayafirat.com/confirm/"+token;
		String messageText= "<!DOCTYPE html><html lang=\"en\"> <head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>Verify Account</title> <style> html { background-color: #F8F9FA; } .center { text-align: center; } .h1 a{ text-align: center; text-decoration: none; color: #0069D9; } .container { margin: auto; width: 40%; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; } .text-muted { color: #747C84; font-size: 12px; } .active { text-align: center; margin-bottom: 40px; } .active button { width: 200px; height: 50px; background-color:#4cad50; border:none; border-radius: 20px; color: #fff; outline: none; font-size: 18px; cursor: pointer;} .active a { color: #fff; font-size: 18px; text-decoration: none; } .active a:hover { cursor: pointer; background-color: #39823B; border:none; outline: none;} .active a button:active { background-color: #4ab14d; outline: none; border:none; box-shadow: 5px 5px 15px #4ab14d; } .text-18 { font-size: 16px; } .text-14 { font-size:14px } @media only screen and (max-width: 600px) { .container { width: 100%; }}</style> </head> <body> <div class=\"container\"> <div class=\"header center\"> <h1 class=\"h1\"> <a href=\"https://kayafirat.com\">kayafirat.com</a> </h1> </div> <div class=\"img center\"> <img src=\"https://kayatech.me/img/padlock.png\" width=\"150px\" alt=\"verify-account\"> </div> <div style=\"font-size: 18px;\"> <p>Sevgili <b>"+user.getUserEmail()+"</b>,</p> <p>Başarılı bir şekilde kayıt işlemin tamamlandı. Lütfen aşağıdaki linke tıklayarak email adresini onaylayabilirsin. </p> <p>Teşekkürler</p> <p class=\"text-14\">Not : Hesabı aktive etme süren 30 dakikadır. Bu link 30 dakika sonra ömrünü tamamlayacaktır.Yeni bir doğrulama maili almak istiyorsan Ayarlar kısmından yeni bir şifre talep edebilirsin.</p> </div> <div class=\"active\"> <a href=\"https://kayafirat.com\"><button>Hesabı Aktive Et</button></a> </div> <div class=\"error\"> Üsteki link çalışmıyorsa lütfen buna tıkla :"+ verificationAddress+" </div> <div class=\"unsubscribe\"> <p class=\"text-muted\"> Bu maili almak istemiyorsanız mail üyeliginizi sonlandırabilirsiniz. <a href=\"#\">Unsubscribe | Mail Üyeliginden çık</a> </p> </div> </div> </body></html>";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		helper.setFrom("kayafirat.com<noreply@kayafirat.com>");
		helper.setSubject("Hesap Onaylama");
		helper.setTo(user.getUserEmail());
		helper.setText(messageText,true);
			try{
				this.mailSender.send(message);
			}
			catch (MailException ex) {
				throw new com.kayafirat.exceptions.customExceptions.MailException(request.get("email"));
			}
	}
	
	@Override
	public void sendResetPasswordEmail(@ExistsEmail HashMap<String, String>  request) throws MessagingException {
		User user = userRepository.findByUserEmail(request.get("email"));
		String token = createToken(user.getUserEmail(),user.getUserId(),env.getProperty("mail.password-code"));
		String verificationAddress = "https://blog.kayafirat.com/forgotpassword/reset/"+token;
		String messageText= "<!DOCTYPE html><html lang=\"en\"> <head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>Verify Account</title> <style> html { background-color: #F8F9FA; } .center { text-align: center; } .h1 a{ text-align: center; text-decoration: none; color: #0069D9; } .container { margin: auto; width: 40%; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; } .text-muted { color: #747C84; font-size: 12px; } .active { text-align: center; margin-bottom: 40px; } .active button { width: 200px; height: 50px; background-color:#4cad50; border:none; border-radius: 20px; color: #fff; outline: none; font-size: 18px; cursor: pointer;} .active a { color: #fff; font-size: 18px; text-decoration: none; } .active a:hover { cursor: pointer; background-color: #39823B; border:none; outline: none;} .active a button:active { background-color: #4ab14d; outline: none; border:none; box-shadow: 5px 5px 15px #4ab14d; } .text-18 { font-size: 16px; } .text-14 { font-size:14px } @media only screen and (max-width: 600px) { .container { width: 100%; }}</style> </head> <body> <div class=\"container\"> <div class=\"header center\"> <h1 class=\"h1\"> <a href=\"https://kayafirat.com\">kayafirat.com</a> </h1> </div> <div class=\"img center\"> <img src=\"https://kayatech.me/img/synchronize.png\" width=\"150px\" alt=\"verify-account\"> </div> <div style=\"font-size: 18px;\"> <p>Sevgili <b>"+user.getUserEmail()+"</b>,</p> <p>Bizlerden istediğin gibi şifreni sıfırlama bağlantısını getirdik. Aşağıdaki <b>Şifremi Sıfırla </b> bağlantısına tıklayarak açılacak yeni sayfadan kendine yeni bir sayfa oluşturabilirsin.</p> <p>Teşekkürler</p> <p class=\"text-14\">Not : Şifre sıfırlama süren 30 dakikadır. Bu link 30 dakika sonra ömrünü tamamlayacaktır.Yeni bir sıfırlama maili almak istiyorsan şifremi unuttun seçeneğime tıklayarak yeni bir link talep edebilirsin.</p> </div> <div class=\"active\"> <a href="+verificationAddress+""+"><button>Şifremi Sıfırla</button></a> </div> <div class=\"error\"> Üsteki link çalışmıyorsa lütfen buna tıkla :"+verificationAddress+" </div> <div class=\"unsubscribe\"> <p class=\"text-muted\"> Bu maili almak istemiyorsanız mail üyeliginizi sonlandırabilirsiniz. <a href=\"#\">Unsubscribe | Mail Üyeliginden çık</a> </p> </div> </div> </body></html>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		helper.setFrom("kayafirat.com<noreply@kayafirat.com>");
		helper.setSubject("Şifre Sıfırlama");
		helper.setTo(user.getUserEmail());
		helper.setText(messageText,true);
			try{
				this.mailSender.send(message);
			}
			catch (MailException ex) {
				throw new com.kayafirat.exceptions.customExceptions.MailException(request.get("email"));
			}
			


	}
	
	@Override
	public String createToken(String email,String id,String code) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(env.getProperty("jwt.secret-key"));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        
        
        JwtBuilder builder = Jwts.builder().setId(email)
                .setIssuedAt(now)
                .setSubject(id)
                .claim("xts", code)
                .setIssuer("kayafirat.com")
                .setExpiration(new Date(nowMillis + (600000 * 3)))
                .signWith(signatureAlgorithm, signingKey);
        
		     return builder.compact();   	    
	}

	@Override
	public void sendSuccessResetPassword(String emailAddress,String ipAddress,String UserAgent) throws MessagingException {
		String messageText= "<!DOCTYPE html><html lang=\"en\"> <head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>Password Changed</title> <style> html { background-color: #F8F9FA; } .center { text-align: center; } .h1 a{ text-align: center; text-decoration: none; color: #0069D9; } .container { margin: auto; width: 40%; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; } .text-muted { color: #747C84; font-size: 12px; } .active { text-align: center; margin-bottom: 40px; } .active button { width: 200px; height: 50px; background-color:#C93D32; border:none; border-radius: 20px; color: #fff; outline: none; font-size: 16px; cursor: pointer;} .active a { color: #fff; font-size: 18px; text-decoration: none; } .active a:hover { cursor: pointer; background-color:#C93D32; border:none; outline: none;} .active a button:active { background-color: #C93D32; outline: none; border:none; box-shadow: 5px 5px 15px #C93D32; } .text-18 { font-size: 16px; } .text-14 { font-size:14px } @media only screen and (max-width: 600px) { .container { width: 100%; }}</style> </head> <body> <div class=\"container\"> <div class=\"header center\"> <h1 class=\"h1\"> <a href=\"https://kayafirat.com\">kayafirat.com</a> </h1> </div> <div class=\"img center\"> <img src=\"https://kayatech.me/img/password.png\" width=\"150px\" alt=\"verify-account\"> </div> <div style=\"font-size: 16px;\"> <p>Sevgili <b>"+emailAddress+"</b>,</p> <p>Hesap şifren <span><b>"+getDate()+"</b></span> tarihinde değiştirildi. Eğer bu sen değilsen lütfen bizimle iletişime geç ve hesabını güvene al.</p> <p class=\"text-muted\">Ip Address: <span>"+ipAddress+"</span> </p> <p class=\"text-muted\">User-Agent: <span>"+UserAgent+"</span></p> </div> <div class=\"active\"> <a href=\"https://kayafirat.com\"><button>Bunu ben yapmadım.</button></a> </div> <div class=\"error\"> Üsteki link çalışmıyorsa lütfen buna tıkla : </div> <div class=\"unsubscribe\"> <p class=\"text-muted\"> Bu maili almak istemiyorsanız mail üyeliginizi sonlandırabilirsiniz. <a href=\"#\">Unsubscribe | Mail Üyeliginden çık</a> </p> </div> </div> </body></html>";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		helper.setFrom("kayafirat.com<noreply@kayafirat.com>");
		helper.setSubject("Şifre Değiştirildi");
		helper.setTo(emailAddress);
		
		helper.setText(messageText,true);
	        try{
	            this.mailSender.send(message);
	        }
	        catch (MailException ex) {
				throw new com.kayafirat.exceptions.customExceptions.MailException(emailAddress);
	        }
		
	}
	
	@Override
	public void sendSuccessVerification(String emailAddress) throws MessagingException {
		String messageText="<!DOCTYPE html><html lang=\"en\"> <head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>Verify Account</title> <style> html { background-color: #F8F9FA; } .center { text-align: center; } .h1 a{ text-align: center; text-decoration: none; color: #0069D9; } .container { margin: auto; width: 40%; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; } .text-muted { color: #747C84; font-size: 12px; } .active { text-align: center; margin-bottom: 40px; } .active button { width: 200px; height: 50px; background-color:#4cad50; border:none; border-radius: 20px; color: #fff; outline: none; font-size: 18px; cursor: pointer;} .active a { color: #fff; font-size: 18px; text-decoration: none; } .active a:hover { cursor: pointer; background-color: #39823B; border:none; outline: none;} .active a button:active { background-color: #4ab14d; outline: none; border:none; box-shadow: 5px 5px 15px #4ab14d; } .text-18 { font-size: 16px; } .text-14 { font-size:14px } @media only screen and (max-width: 600px) { .container { width: 100%; }}</style> </head> <body> <div class=\"container\"> <div class=\"header center\"> <h1 class=\"h1\"> <a href=\"https://kayafirat.com\">kayafirat.com</a> </h1> </div> <div class=\"img center\"> <img src=\"https://kayatech.me/img/padlock.png\" width=\"150px\" alt=\"verify-account\"> </div> <div style=\"font-size: 18px;\"> <p>Sevgili <b>"+emailAddress+"</b>,</p> <p>Hesabın başarıyla aktive edildi ve kullanıma hazır. <a href=\"https://blog.kayafirat.com/login\">Buraya </a> tıklayarak giriş yapabilir ve kullanabilirsin.</p> <p>Bol okumalı günler dilerim.</p> </div> <div class=\"unsubscribe\"> <p class=\"text-muted\"> Bu maili almak istemiyorsanız mail üyeliginizi sonlandırabilirsiniz. <a href=\"#\">Unsubscribe | Mail Üyeliginden çık</a> </p> </div> </div> </body></html>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		helper.setFrom("kayafirat.com<noreply@kayafirat.com>");
		helper.setSubject("Hesap Onaylandı");
		helper.setTo(emailAddress);
		helper.setText(messageText,true);
	        try{
	            this.mailSender.send(message);
	        }
	        catch (MailException ex) {
				throw new com.kayafirat.exceptions.customExceptions.MailException(emailAddress);
	        }
		
	}

	public String getDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm");
		return simpleDateFormat.format(new Date());
	}

	

}
