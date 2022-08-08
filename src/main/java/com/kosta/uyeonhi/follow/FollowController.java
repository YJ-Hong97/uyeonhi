package com.kosta.uyeonhi.follow;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

@RestController
public class FollowController {
	@Autowired
	FollowRepository fRepo;
	@Autowired
	UserRepository uRepo;
	
	@PostMapping(value = "/auth/dofollow")
	public String follow(String id, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		
		Follow follow = Follow.builder()
				.user(user)
				.target(uRepo.findById(id).get())
				.fCheck(false)
				.build();
		
		fRepo.save(follow);
		
		return "Success";
	}
	
	@GetMapping(value = "/listfollow")
	public List<Follow> listfollow(String id) {
		List<Follow> follows = fRepo.findByTarget(uRepo.findById(id).get());
		
		return follows;
	}
	
	@GetMapping(value = "/listfollowing")
	public List<Follow> listfollowing(String id) {
		List<Follow> follows = fRepo.findByUser(uRepo.findById(id).get());
		
		return follows;
	}
	
	@DeleteMapping(value = "/auth/dofollow")
	public String unfollow(String id, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		
		fRepo.delete(fRepo.checkFollow(id, user.getId()));
		
		return "Success";
	}
}
