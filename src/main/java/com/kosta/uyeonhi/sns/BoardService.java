package com.kosta.uyeonhi.sns;

import com.kosta.uyeonhi.signUp.UserVO;

public interface BoardService {
	
	Long register(BoardDTO boardDTO);
	
	
	
	
	default Board dtoToEntity(Board board, UserVO userVO, int replyCount) {
		
		BoardDTO boardDTO = BoardDTO.builder()
				.board_id(board.getBoard_id())
				.content(board.getContent())
				.regdate(board.getRegdate())
				.update_date(board.getUpdateDate())
				.writerNickname(userVO.getEmail())
				.writerEmail(userVO.getEmail())
				
				.replyCount(replyCount)
				.build();
		
		return board;
	};
}
