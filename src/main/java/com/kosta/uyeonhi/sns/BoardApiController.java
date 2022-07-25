package com.kosta.uyeonhi.sns;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RequestMapping("/sns/")
@Log4j2
@RestController
public class BoardApiController {
	@Autowired
	private  BoardRepository boardRepository;
	
	@Autowired
	private  BoardService boardService;
	
	@PostMapping("/boardWrite")
	public ResponseEntity<?> boardWritePost(@RequestBody BoardRequestDTO boardDTO, HttpSession session) {
		session.getAttribute("");
		System.out.println(boardDTO.toString());
		return new ResponseEntity<>(boardService.saveBoard(boardDTO), HttpStatus.CREATED);
	}

}
