package com.kosta.uyeonhi.security;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;


//config에 요청이있다면 RequestMapping은 필수
@Controller
public class SecurityController {

	@Autowired
	MemberService mservice;
	
	
	@GetMapping("/logout")
	public void logout() {
		
	}
	// 
	@RequestMapping("/auth/loginSuccess")
	public String loginSuccess(Principal principal) {
		String mid = principal.getName();
		UserDetails userDetails3 = mservice.loadUserByUsername(mid);
		System.out.println("방법4:" +userDetails3);
		System.out.println("/auth/loginSuccess");
		
		return "redirect:/sns/sns1";
	}
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		System.out.println("/auth/accessDenied");
		return "/auth/accessDenied";
	}
	
	
}
