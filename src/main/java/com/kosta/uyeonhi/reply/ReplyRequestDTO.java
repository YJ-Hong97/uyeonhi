package com.kosta.uyeonhi.reply;

import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class ReplyRequestDTO {
	
	private String content;
	private Long boardId;
	private UserVO user;
	private Long parentId;
	private String depth;
	
	public Reply toEntity() {
		return Reply.builder()
				.reply_content(content)
				.depth(depth)
				.build();
	}
	
}
