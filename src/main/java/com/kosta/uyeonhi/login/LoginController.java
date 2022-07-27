package com.kosta.uyeonhi.login;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.uyeonhi.security.MemberService;

@Controller
@RequestMapping("/auth")
public class LoginController {
	@Autowired
	MemberService mservice;
	
	@RequestMapping("/login")
	public String loginPage() {
		return "/auth/login";
	}
	
	@GetMapping(value = "/myPage")
	public void myPage() {
		
	}
}
