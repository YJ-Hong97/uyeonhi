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
	//@Transactional(readOnly = true)
	public List<Board> pageList(){//Pageable pageable
		return (List<Board>) boardRepository.findAll();
	}
	
	@Override
	@Transactional //삭제
	public void delete(long board_id) {
		Board board = boardRepository.findById(board_id).orElseThrow(() -> 
		new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + board_id));

		boardRepository.deleteById(board_id);
	}
	
	@Override
	@Transactional
	public void update(long board_id, BoardRequestDTO boardRequestDTO) {
		// TODO Auto-generated method stub
		Board board = boardRepository.findById(board_id).orElseThrow(() -> new
		IllegalArgumentException("해당 게시물이 존재하지 않습니다. " + board_id));
		
		board.setContent(boardRequestDTO.getContent()); 
	}

	/*
	 * @Override
	 * 
	 * @Transactional //수정 
	 */



	
}
