package com.kosta.uyeonhi.reply;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.sns.Board;
import com.kosta.uyeonhi.sns.BoardRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //private final + AllArgsConstructor로 생성자 주입시 @Autowired생략
public class ReplyServiceImpl implements ReplyService{

	private final ReplyRepository replyRepository;
	
	private final BoardRepository boardRepository;
	
	private final UserRepository userRepository;
	
	
	@Override
	public void writeReply(Long boardId, ReplyRequestDTO replyRequestDTO) {
		
		Reply parent = replyRepository.findByReplyId(replyRequestDTO.getParentId());
		Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException());
		
		Reply reply = Reply.builder()
				.reply_content(replyRequestDTO.getContent())
				.user(replyRequestDTO.getUser())
				.board(board)
				.depth(replyRequestDTO.getDepth())
				.parent(parent)
				.build();
		replyRepository.save(reply);
	}
	@Override
	public List<ReplyResponseDTO> getReplyList(Board board) {
		List<ReplyResponseDTO> replyList = replyRepository.findByBoard(board);
		return replyList;
	}
	
	@Override
	public Long deleteReply(Long replyId) {
		Reply reply = replyRepository.findById(replyId).orElseThrow(() -> 
		new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + replyId));

		replyRepository.deleteById(replyId);

		return replyId;
	}
	@Override
	public void updateReply(Long replyId, String updateCon) {
		Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("댓글 찾기 실패");
                }); //영속화 완료
		reply.setReply_content(updateCon);
		replyRepository.save(reply);
	}
	
	

}
