package com.kosta.uyeonhi.webrtc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
@Entity(name = "videoChatMessage")
public class VideoChatMessageVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long messageId;
	@ManyToOne
	private VideoChatVO videoChatVO;
	private String nickname;
	private String message;
}
