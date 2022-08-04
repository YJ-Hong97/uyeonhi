package com.kosta.uyeonhi.mypage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Entity
@Table
public class ChattingUsersVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long chatUserId;
	@ManyToOne
	private ChattingRoomVO room;
	@ManyToOne
	private UserVO user;
	private ChatUserState state;
}
