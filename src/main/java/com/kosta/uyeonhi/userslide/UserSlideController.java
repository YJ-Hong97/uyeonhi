package com.kosta.uyeonhi.userslide;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserSlideController {
	@GetMapping("/userslide")
	public String userslider() {
		
		return "/fragment/userslider";
	}
}
