package com.kosta.uyeonhi.reply;

import java.util.List;

import com.kosta.uyeonhi.sns.Board;

public interface ReplyService {
		
	Long writeReply(Long boardId, ReplyRequestDTO replyRequestDTO);
	
	List<Reply> getReplyList(Long boardId);

}
