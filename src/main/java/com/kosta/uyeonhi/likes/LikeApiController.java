package com.kosta.uyeonhi.likes;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.uyeonhi.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeApiController {
	
	private final LikesRepository likesRepository;
	
	@Transactional
	@PostMapping("/sns/likes/{board_id}")
	public ResponseEntity<?> likes(@PathVariable Long board_id){
		System.out.println(board_id);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
		
		likesRepository.likes(board_id , username );
		return new ResponseEntity<>("좋아요 성공",HttpStatus.OK);
		
	}
	
	@Transactional
	@DeleteMapping("/sns/notlikes/{board_id}")
	public ResponseEntity<?> notLikes(@PathVariable Long board_id){
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
		
		likesRepository.notLikes(board_id , username );
		return new ResponseEntity<>("좋아요 취소 성공",HttpStatus.OK);
		
	}
}
