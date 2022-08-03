package com.kosta.uyeonhi.mypage;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;

public interface ChattingRoomRepository extends CrudRepository<ChattingRoomVO, Long>{
	List<ChattingRoomVO> findByUser(UserVO user);

}
