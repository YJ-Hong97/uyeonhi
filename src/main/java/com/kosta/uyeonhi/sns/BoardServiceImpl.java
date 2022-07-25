package com.kosta.uyeonhi.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

	@Autowired
	private final BoardRepository boardRepository;
	
	@Override
	public Long saveBoard(BoardRequestDTO boardDTO) {
		log.info(boardDTO);
		return boardRepository.save(boardDTO.toEntity()).getBoardId();
	}

	@Override
	public void delete(int board_id) {
		// TODO Auto-generated method stub
	}

	@Override
	//@Transactional(readOnly = true)
	public List<Board> pageList(){//Pageable pageable
		return (List<Board>) boardRepository.findAll();
	}



	
}
