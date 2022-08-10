package com.kosta.uyeonhi.mypage;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;

public interface ChattingUsersRepository extends CrudRepository<ChattingUsersVO, Long>{
	List<ChattingUsersVO> findByRoom(ChattingRoomVO room);
	List<ChattingUsersVO> findByUser(UserVO user);
	@Modifying
	@Transactional
	void deleteByRoom(ChattingRoomVO room);
}
