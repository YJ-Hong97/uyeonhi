package com.kosta.uyeonhi.sns;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kosta.uyeonhi.signUp.UserVO;

public interface BoardService {
	
	
	List<Board> pageList(); //Pageable pageable
		
	Long saveBoard(BoardRequestDTO boardDTO);
		
	void delete(int board_id);


}
