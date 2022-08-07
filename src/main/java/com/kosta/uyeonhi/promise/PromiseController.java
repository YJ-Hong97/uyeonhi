package com.kosta.uyeonhi.promise;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.uyeonhi.signUp.UserVO;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/uyeonhi/*")
public class PromiseController {
	
	@RequestMapping("/promise")
	public ModelAndView promise(ModelAndView mv, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		
		mv.addObject("id", user.getId());
		mv.setViewName("promise/promise");
		return mv;
	}
	
	@RequestMapping("/modal")
	public ModelAndView modal(ModelAndView mv) {
		mv.setViewName("promise/modal");
		return mv;
	}
}
