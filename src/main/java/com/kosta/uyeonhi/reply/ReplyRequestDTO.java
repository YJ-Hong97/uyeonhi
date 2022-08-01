package com.kosta.uyeonhi.reply;

import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class ReplyRequestDTO {
	
	private String content;
	private Board board;
	private UserVO user;
	
	public Reply toEntity() {
		return Reply.builder()
				.reply_content(content)
				.board(board)
				.user(user)
				.build();
	}
}
