package com.kosta.uyeonhi.promise;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

		int count = proRepo.selectByMonth(LocalDate.now().getMonth().getValue(), user.getId());

		mv.addObject("count", count);
		mv.addObject("plist", proRepo.findByMe(user));
		mv.setViewName("promise/promise");
		return mv;
	}

	@RequestMapping("/promiseList")
	public List<PromiseVO> promiseList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		System.out.println(proRepo.findByMe(user));
		return proRepo.findByMe(user);
	}

	@PostMapping("/detail")
	public ModelAndView modal(ModelAndView mv, @RequestBody Map<String, String> map, HttpServletRequest request)
			throws ParseException {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");

		PromiseVO vo = proRepo.selectByDetail(user.getId(), map.get("date2"), map.get("title"));
		mv.addObject("promise", vo);
		mv.addObject("date", map.get("date"));
		mv.addObject("date2", map.get("date2"));
		mv.setViewName("promise/modal");
		return mv;
	}

	@PostMapping("/cancel")
	public String cancel(HttpServletRequest request, @RequestBody Map<String, String> map) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");

		
		PromiseVO vo = proRepo.selectByDetail(user.getId(), map.get("date"), map.get("title"));
		vo.setCancel_ox("o"); 
		vo.setPromise_ox("x"); 
		vo.setReason(map.get("reason")); 
		System.out.println(vo);
		
		PromiseVO check = proRepo.save(vo); 
		if(!(check.getCancel_ox().equals("o"))) 
			return "fail";
		 
		return "success";
	}
}
