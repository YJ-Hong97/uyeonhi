package com.kosta.uyeonhi.likes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.uyeonhi.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sns/")
public class LikeApiController {
	
	private final LikesRepository likesRepository;
	
	@PostMapping("/sns1/{boardId}/likes")
	public ResponseEntity<?> likes(@PathVariable long boardId, @AuthenticationPrincipal SecurityUser user){
		likesRepository.likes(boardId, user.getUser().getId());
		return new ResponseEntity<>("좋아요 성공",HttpStatus.OK);
		
	}
	
	@DeleteMapping("/sns1/{boardId}/likes")
	public ResponseEntity<?> notLikes(@PathVariable long boardId, @AuthenticationPrincipal SecurityUser user){
		likesRepository.notLikes(boardId, user.getUser().getId());
		return new ResponseEntity<>("좋아요 취소 성공",HttpStatus.OK);
		
	}
}
