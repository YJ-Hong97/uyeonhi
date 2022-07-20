package com.kosta.uyeonhi.repositoryTest;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;
import com.kosta.uyeonhi.sns.BoardRepository;
import com.kosta.uyeonhi.sns.Sns;

@SpringBootTest
public class BoardRepositoryTest {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Test
	public void insertBoard() {
		IntStream.rangeClosed(1, 100).forEach(i->{
			UserVO userVO = UserVO.builder().id("user"+i).build();
			
		});
	}
	
		
	
}
