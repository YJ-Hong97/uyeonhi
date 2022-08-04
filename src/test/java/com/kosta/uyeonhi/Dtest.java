package com.kosta.uyeonhi;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.uyeonhi.mypage.ChatType;
import com.kosta.uyeonhi.mypage.ChatUserState;
import com.kosta.uyeonhi.mypage.ChattingRoomRepository;
import com.kosta.uyeonhi.mypage.ChattingRoomVO;
import com.kosta.uyeonhi.mypage.ChattingUsersRepository;
import com.kosta.uyeonhi.mypage.ChattingUsersVO;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

@SpringBootTest
public class Dtest {
	@Autowired
	UserRepository uRepo;
	@Autowired
	ChattingRoomRepository chatRoomRepo;
	@Autowired
	ChattingUsersRepository chatUserRepo;
	@Test
	public void test() {
		UserVO user = uRepo.findById("pung").get();
		ChattingRoomVO room = ChattingRoomVO.builder()
				.title("뿡뿡이와 나")
				.user(user)
				.type(ChatType.video)
				.build();
		chatRoomRepo.save(room);
		ChattingUsersVO cuser = ChattingUsersVO.builder()
				.user(user)
				.state(ChatUserState.unconnect)
				.room(room)
				.build();
		chatUserRepo.save(cuser);
		ChattingUsersVO user2 = ChattingUsersVO.builder()
				.user(uRepo.findById("hyj1077").get())
				.state(ChatUserState.unconnect)
				.room(room)
				.build();
		chatUserRepo.save(user2);
		
	}
}
