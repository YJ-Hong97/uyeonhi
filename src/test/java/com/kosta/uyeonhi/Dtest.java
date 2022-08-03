package com.kosta.uyeonhi;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.uyeonhi.mypage.ChatType;
import com.kosta.uyeonhi.mypage.ChattingRoomRepository;
import com.kosta.uyeonhi.mypage.ChattingRoomVO;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

@SpringBootTest
public class Dtest {
	@Autowired
	UserRepository uRepo;
	@Autowired
	ChattingRoomRepository chatRoomRepo;
	@Test
	public void test() {
		UserVO user = uRepo.findById("ijbmsm").get();
		ChattingRoomVO room = ChattingRoomVO.builder()
				.title("우연히 화이팅")
				.type(ChatType.voice)
				.user(user)
				.build();
		chatRoomRepo.save(room);
		ChattingRoomVO room2 = ChattingRoomVO.builder()
				.title("화상채팅 망항")
				.type(ChatType.video)
				.user(user)
				.build();
		chatRoomRepo.save(room2);
		
	}
}
