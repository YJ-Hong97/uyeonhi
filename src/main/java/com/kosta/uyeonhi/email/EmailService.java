package com.kosta.uyeonhi.email;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;


@Service
public class EmailService {
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	EmailTokenService emailTokenService;
	@Autowired
	UserRepository uRepo;
	
	
	public void sendMail(SimpleMailMessage email) {
		javaMailSender.send(email);
	}
	public void verifyEmail(String token) {
		EmailToken findEmailToken = emailTokenService.findByIdAndExpirationDateAfterAndExpired(token);
		
		Optional<UserVO> findUser = uRepo.findById(findEmailToken.getUserId());
		findEmailToken.setTokenToUsed();//사용완
		
		if(findUser.isPresent()) {
			UserVO user = findUser.get();
		}
	}
}
