package com.kosta.uyeonhi.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosta.uyeonhi.signUp.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

	@Autowired
	private final BoardRepository boardRepository;
	
	@Override
	public void saveBoard(BoardDTO boardDTO) {
		log.info(boardDTO);
		// boardRepository.save(boardService.dtoToEntity(boardDTO)).getBoard_id();
	}

	@Override
	public void delete(int board_id) {
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Board> pageList(Pageable pageable){
		return boardRepository.findAll(pageable);
	}


	
}
