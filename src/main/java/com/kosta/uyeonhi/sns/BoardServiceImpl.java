package com.kosta.uyeonhi.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosta.uyeonhi.likes.Likes;
import com.kosta.uyeonhi.likes.LikesRepository;

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
		return boardRepository.save(boardDTO.toEntity()).getBoardId();
	}
	@Override
	public Page<Board> pageList(Pageable pageable){//Pageable pageable
		return boardRepository.findAll(pageable);
	}
	
	@Override
	@Transactional //삭제
	public void delete(long board_id) {
		Board board = boardRepository.findById(board_id).orElseThrow(() -> 
		new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + board_id));

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
	@Override
	public Board findByBoardID(long board_id) {
		
		return boardRepository.findById(board_id).orElseThrow(()-> new NullPointerException());
	}
	
	@Override
	@Transactional
	public List<Board> tagSearch(String tag){
		 System.out.println("BoardServiceImpl : "+tag);
		return boardRepository.findByTagContaining(tag);
	}

	@Override
	@Transactional
	public void recruitApply(long board_id,BoardRequestDTO boardRequestDTO) {
		// TODO Auto-generated method stub
		Board board = boardRepository.findById(board_id).orElseThrow(() -> new
		IllegalArgumentException("해당 게시물이 존재하지 않습니다. " + board_id));
		board.setApplicant_person(boardRequestDTO.getApplicant_person()); 
		
	}
	
	/*
	 * @Override
	 * 
	 * @Transactional //수정 
	 */



	
}
