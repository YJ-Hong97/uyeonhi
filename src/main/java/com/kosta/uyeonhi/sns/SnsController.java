package com.kosta.uyeonhi.sns;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sns/*")
@Controller
public class SnsController {

	@GetMapping("/sns1.go")
	public void insert() {
		
	}
}
