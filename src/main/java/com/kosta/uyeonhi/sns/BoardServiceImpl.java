package com.kosta.uyeonhi.sns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

	@Autowired
	private final BoardRepository boardRepository;
	
	@Override
	public Long register(BoardDTO boardDTO) {
		log.info(boardDTO);
		Board board = dtoToEntity(boardDTO);
		boardRepository.save(board);
		return board.getBoard_id();
	}
	
}
