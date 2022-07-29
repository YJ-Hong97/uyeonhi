package com.kosta.uyeonhi.mypage;

import java.util.List;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.util.PageMaker;
import com.kosta.uyeonhi.util.PageVO;

import lombok.extern.log4j.Log4j2;
@Log4j2
@RestController
public class MyPageController {
	@Autowired
	NotToMeetRepository nRepo;
	@Autowired
	InquiryRepository iRepo;
	
	@GetMapping("/setting")
	public ModelAndView settingModal(ModelAndView mnv) {
		mnv.setViewName("/mypage/main");
		return mnv;
	}
	
	@PostMapping("/notToMeet")
	public List<NotToMeetVO> notToMeet(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		List<NotToMeetVO> blockList = nRepo.findByUser(user);
		 
		return blockList;
	}
	@PostMapping("/newBlock")
	public String newBlock(String bname,String bphone,HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		boolean result = nRepo.existsByPhoneAndUser(bphone, user);
		if(result) {
			
			return "fail";
		}else {
			NotToMeetVO n = NotToMeetVO.builder()
					.name(bname)
					.phone(bphone)
					.user(user)
					.build();
			nRepo.save(n);
			return "success";
		}
		
	}
	@PostMapping("dBlock/{phone}")
	public void dBlock(@PathVariable String phone,HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		nRepo.deleteBlock(user, phone);
	}
	@GetMapping("inquiry")
	public ModelAndView inquiry(ModelAndView mnv, HttpServletRequest request,PageVO pageVO) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		mnv.addObject("user",user);
		mnv.setViewName("/mypage/inquiry");
		
		Pageable page = pageVO.makePageable(0,1,"inquiryId");
		Page<InquiryVO> result = iRepo.findAll(iRepo.makePredicate(null, null), page);
		
		mnv.addObject("result",new PageMaker<>(result));
		return mnv;
		
	}
	@GetMapping("inquiryPage/{pageNum}")
	public ModelAndView inquiryPage(ModelAndView mnv, HttpServletRequest request, PageVO pageVO,@PathVariable("pageNum") int pagenum) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		mnv.addObject("user",user);
		mnv.setViewName("/mypage/inquiry");
		
		Pageable page = pageVO.makePageable(0,pagenum,"inquiryId");
		Page<InquiryVO> result = iRepo.findAll(iRepo.makePredicate(null, null), page);
		
		mnv.addObject("result",new PageMaker<>(result));
		return mnv;
	}
	@GetMapping("newInquiry")
	public ModelAndView newInquiry(ModelAndView mnv, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		mnv.addObject("user",user);
		mnv.setViewName("/mypage/newInquiry");
		return mnv;
		
	}
	
}
