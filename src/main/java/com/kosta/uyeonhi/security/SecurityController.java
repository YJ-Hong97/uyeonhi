package com.kosta.uyeonhi.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public void loginSuccess() {
		System.out.println("/auth/loginSuccess");
	}
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		System.out.println("/auth/accessDenied");
		return "/auth/accessDenied";
	}
	
	
}
