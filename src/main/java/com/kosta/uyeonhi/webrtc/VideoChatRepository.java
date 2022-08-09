package com.kosta.uyeonhi.webrtc;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.mypage.ChattingRoomVO;

public interface VideoChatRepository extends CrudRepository<VideoChatVO, Long>{
	VideoChatVO findByRoom(ChattingRoomVO room);
	boolean existsByRoom(ChattingRoomVO room);
	@Modifying
	@Transactional
	void deleteByRoom(ChattingRoomVO room);
}
