package com.kosta.uyeonhi.email;

import java.util.Date;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
	@Autowired
	JavaMailSender javaMailSender;
	
	public void sendMail(String email, Map<String, String> signUpInfo) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		message.setSubject("안녕하세요 우연히입니다.");
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		
		String str = "안녕하세요 우연히입니다.\n"+"아래의 링크로 회원가입을 완료해주세요.\n"+"http://localhost:7777/uyeonhi/signUp4/"+signUpInfo;
		message.setText(str);		
		message.setSentDate(new Date());
		javaMailSender.send(message);
	}
}
