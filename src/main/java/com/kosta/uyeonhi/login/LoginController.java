package com.kosta.uyeonhi.login;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.uyeonhi.follow.Follow;
import com.kosta.uyeonhi.follow.FollowRepository;
import com.kosta.uyeonhi.security.MemberService;
import com.kosta.uyeonhi.signUp.UserVO;

@Controller
@RequestMapping("/auth")
public class LoginController {
	@Autowired
	MemberService mservice;
	
	@Autowired
	FollowRepository fRepo;
	
	@RequestMapping("/login")
	public String loginPage() {
		return "/auth/login";
	}
	
//	@GetMapping(value = "/myPage")
//	public String myPage(String mid, Model model, HttpSession session) {
//		UserVO user = (UserVO) session.getAttribute("user");
//		mservice.getUserProfile(mid, model);
//		Follow follow = fRepo.checkFollow(mid, user.getId());
//		
//		model.addAttribute("isFollow", follow != null ? 1 : 0);
//		model.addAttribute("follower", fRepo.countFollower(mid));
//		model.addAttribute("following", fRepo.countFollowing(mid));
//		
//		return "/auth/myPage";
//	}
}
