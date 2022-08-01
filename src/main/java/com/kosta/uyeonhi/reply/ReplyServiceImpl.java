package com.kosta.uyeonhi.reply;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kosta.uyeonhi.sns.Board;
import com.kosta.uyeonhi.sns.BoardRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //private final + AllArgsConstructor로 생성자 주입시 @Autowired생략
public class ReplyServiceImpl implements ReplyService{

	private final ReplyRepository replyRepository;
	
	private final BoardRepository boardRepository;
	@Override
	public Long writeReply(Long boardId, ReplyRequestDTO replyRequestDTO) {
		Reply reply = replyRequestDTO.toEntity();
		reply.setBoard(boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException()));
		System.out.println(reply.toString());
		return replyRepository.save(reply).getReply_id();
	}
	@Override
	public List<Reply> getReplyList(Long boardId) {
		List<Reply> replyList = replyRepository.getRepliesByBoardId(boardId);
		return replyList;
	}

}
