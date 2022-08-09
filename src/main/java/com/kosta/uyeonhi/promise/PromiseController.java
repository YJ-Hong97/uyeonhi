package com.kosta.uyeonhi.promise;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.uyeonhi.signUp.UserVO;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/uyeonhi/*")
public class PromiseController {
	
	@Autowired
	PromiseRepository proRepo;
	
	@RequestMapping("/promise")
	public ModelAndView promise(ModelAndView mv, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		
		mv.addObject("id", user.getId());
		mv.addObject("plist", proRepo.findByMe(user));
		mv.setViewName("promise/promise");
		return mv;
	}
	
	@RequestMapping("/promiseList")
	public List<PromiseVO> promiseList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		
		return proRepo.findByMe(user);
	}
	
	
	
	@PostMapping("/detail")
	@ResponseBody
	public ModelAndView modal(ModelAndView mv, HttpServletRequest request, @RequestBody Map<String, String> map) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		System.out.println("json!!  "+map);
		mv.setViewName("promise/modal");
		return mv;
	}
}
