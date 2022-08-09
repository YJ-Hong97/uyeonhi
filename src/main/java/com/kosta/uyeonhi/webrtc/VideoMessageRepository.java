package com.kosta.uyeonhi.webrtc;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface VideoMessageRepository extends CrudRepository<VideoChatMessageVO,Long>{
	List<VideoChatMessageVO> findByVideoChatVO(VideoChatVO videoChatVO);
}
