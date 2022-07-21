package com.kosta.uyeonhi.repositoryTest;

import java.sql.Date;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kosta.uyeonhi.signUp.Gender;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;
import com.kosta.uyeonhi.sns.BoardRepository;

public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void insertUser() {
		Date day = new java.sql.Date(System.currentTimeMillis());
		IntStream.rangeClosed(1, 100).forEach(i->{
			UserVO userVO = UserVO.builder().id("user"+i)
					.password("user"+i)
					.email("user"+i+"@naver.com")
					.name("유승민"+i)
					.nickname("유승민"+i)
					.address("서울시")
					.phone("010-7477-1455")
					.gender(Gender.MALE)
					.profile("?")
					.snsConfirm("?")
					.machingConfirm("?")
					.birth(day)
					.build();
			
			
			userRepository.save(userVO);
		});
	}
	
}
