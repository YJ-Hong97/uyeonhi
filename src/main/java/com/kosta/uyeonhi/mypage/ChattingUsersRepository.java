package com.kosta.uyeonhi.mypage;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;

public interface ChattingUsersRepository extends CrudRepository<ChattingUsersVO, Long>{
	List<ChattingUsersVO> findByRoom(ChattingRoomVO room);
	List<ChattingUsersVO> findByUser(UserVO user);
}
