package com.kosta.uyeonhi.login;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.uyeonhi.follow.FollowRepository;
import com.kosta.uyeonhi.security.MemberService;
import com.kosta.uyeonhi.signUp.UserRepository;

@RestController
@RequestMapping("/auth")
public class LoginController {
	@Autowired
	MemberService mservice;
	
	@Autowired
	FollowRepository fRepo;
	@Autowired
	UserRepository uRepo;
	
	@PostMapping("/login/kakao/{id}")
	public boolean checkKakao(@PathVariable("id")String id) {
		return uRepo.existsById(id);
	}
	
	@RequestMapping("/login")
	public ModelAndView loginPage(ModelAndView mnv) {
		mnv.setViewName("index");
		return mnv;
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
