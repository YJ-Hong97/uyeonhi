package com.kosta.uyeonhi.mypage;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;

public interface ChattingRoomRepository extends CrudRepository<ChattingRoomVO, Long>{
	List<ChattingRoomVO> findByUser(UserVO user);
	@Modifying
	@Transactional
	void deleteByRoomNo(Long roomNo);
}
