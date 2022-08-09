package com.kosta.uyeonhi.webrtc;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.firebase.messaging.Message;
import com.kosta.uyeonhi.mypage.ChattingRoomVO;
import com.kosta.uyeonhi.signUp.MFavoriteVO;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "videoChat")
public class VideoChatVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long videoChatId;
	@ManyToOne
	private ChattingRoomVO room;
	
}
