package com.kosta.uyeonhi.follow;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.uyeonhi.signUp.UserVO;

@RestController
public class FollowController {
	@Autowired
	FollowRepository fRepo;
	
	@PostMapping(value = "/auth/dofollow")
	public String follow(String id, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		
		Follow follow = Follow.builder()
				.user(user)
				.target(id)
				.fCheck(false)
				.build();
		
		fRepo.save(follow);
		
		return "Success";
	}
	
	@DeleteMapping(value = "/auth/dofollow")
	public String unfollow(String id, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		
		fRepo.delete(fRepo.checkFollow(id, user.getId()));
		
		return "Success";
	}
}
