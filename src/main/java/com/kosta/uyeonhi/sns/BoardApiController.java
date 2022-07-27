package com.kosta.uyeonhi.sns;


import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class BoardApiController {
	@Autowired
	private  BoardRepository boardRepository;
	
	@Autowired
	private  BoardService boardService;
	
	@PostMapping("/api/sns/boardWrite")
	public ResponseEntity<?> boardWritePost(@RequestBody BoardRequestDTO boardDTO, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        model.addAttribute("username", username);
		return new ResponseEntity<>(boardService.saveBoard(boardDTO), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/api/sns/boardDelete/{board_id}")
	public Long delete(@PathVariable Long board_id) {
		boardService.delete(board_id); 
		return board_id;
	}
	
	@PutMapping("/api/sns/boardUpdate/{board_id}")
	public Long update(@PathVariable Long board_id, @RequestBody BoardRequestDTO boardDTO) {
		
		System.out.println(board_id + boardDTO.toEntity().toString());
		boardService.update(board_id, boardDTO);
		return board_id;
	}

}
