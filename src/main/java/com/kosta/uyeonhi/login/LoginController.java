package com.kosta.uyeonhi.login;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/auth")
public class LoginController {
	@RequestMapping("/login")
	public String loginPage() {
		return "/auth/login";
	}
	
	
}
