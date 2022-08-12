package com.kosta.uyeonhi.email;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import antlr.TokenWithIndex;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailTokenService {
	@Autowired
	EmailService emailService;
	@Autowired
	EmailTokenRepository eRepo;
	
	public String createEmailToken(String receiverEmail) {
		EmailToken emailToken = EmailToken.createEmailToken(receiverEmail);
		eRepo.save(emailToken);
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(receiverEmail);
		mailMessage.setSubject("안녕하세요 우연히입니다.");
		mailMessage.setText("http://3.39.158.96:7777/uyeonhi/validEmail/"+emailToken.getId()+"/"+receiverEmail);	
		
		emailService.sendMail(mailMessage);
		return emailToken.getId();
		
	}
	public EmailToken findByIdAndExpirationDateAfterAndExpired(String emailTokenId) {
		Optional<EmailToken> emailToken = eRepo.findByIdAndExpirationDateAfterAndExpired(emailTokenId, LocalDateTime.now(), false);
		
		return emailToken.get();
	}
}
