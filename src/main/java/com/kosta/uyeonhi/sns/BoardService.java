package com.kosta.uyeonhi.sns;

import com.kosta.uyeonhi.signUp.UserVO;

public interface BoardService {
	
	default Board dtoToEntity(BoardDTO boardDTO) {
		
		Board board = Board.builder()
				.board_id(boardDTO.getBoard_id())
				.content(boardDTO.getContent())
				.regdate(boardDTO.getRegdate())
				.updateDate(boardDTO.getUpdate_date())
				.board_type(boardDTO.getBoard_type())
				.build();
		
		return board;
	};
	
	default BoardDTO entityToDTO(Board board, UserVO userVO, Long replyCount) {
		BoardDTO boardDTO = BoardDTO.builder()
				.board_id(board.getBoard_id())
				.content(board.getContent())
				.regdate(board.getRegdate())
				.update_date(board.getUpdateDate())
				.writerEmail(userVO.getEmail())
				.writerNickname(userVO.getNickname())
				.build();
		return boardDTO;
	}
	
	Long saveBoard(BoardDTO boardDTO);
	
	void delete(int board_id);
}
