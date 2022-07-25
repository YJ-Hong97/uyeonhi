package com.kosta.uyeonhi.email;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailToken {
	private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE=5L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2",strategy = "uuid2")
	@Column(length = 36)
	private String id;
	
	private LocalDateTime expirationDate;
	private boolean expired;
	private String userId;
	
	public static EmailToken createEmailToken(String useId) {
		EmailToken emailToken = new EmailToken();
		emailToken.expirationDate = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE);
		
		emailToken.expired = false;
		emailToken.userId = useId;
		
		return emailToken;
	}
	//토큰 만료
	public void setTokenToUsed() {
		this.expired = true;
	}
}
