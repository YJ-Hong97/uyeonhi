package com.kosta.uyeonhi.reply;

import java.util.List;

import com.kosta.uyeonhi.sns.Board;

public interface ReplyService {
		
	void writeReply(Long boardId, ReplyRequestDTO replyRequestDTO);
	
	List<ReplyResponseDTO> getReplyList(Board board);
	
	Long deleteReply(Long replyId);
	
	void updateReply(Long replyId, String updateCon);

}
