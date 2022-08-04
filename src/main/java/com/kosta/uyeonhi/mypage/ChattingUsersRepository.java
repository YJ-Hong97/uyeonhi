package com.kosta.uyeonhi.mypage;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ChattingUsersRepository extends CrudRepository<ChattingUsersVO, Long>{
	List<ChattingUsersVO> findByRoom(ChattingRoomVO room);
}
