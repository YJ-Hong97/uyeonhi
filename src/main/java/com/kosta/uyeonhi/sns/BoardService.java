package com.kosta.uyeonhi.sns;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kosta.uyeonhi.signUp.UserVO;

public interface BoardService {
	
	
	Page<Board> pageList(Pageable pageable); //
		
	Long saveBoard(BoardRequestDTO boardDTO);
		
	void delete(long board_id);
	
	void update(long board_id, BoardRequestDTO boardRequestDTO);

	Board findByBoardID(long board_id);

}
