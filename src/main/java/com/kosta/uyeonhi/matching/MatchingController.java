package com.kosta.uyeonhi.matching;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.uyeonhi.signUp.UserVO;

@Controller
public class MatchingController {

	@Autowired
	MatchingRepository mRepo;

	@GetMapping(value = "/matching")
	@ResponseBody
	public String matchingRequest(String target, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		System.out.println(user);
		MatchingVO vo = MatchingVO.builder()
				.id(user.getId())
				.target(target)
				.m_confirm(0)
				.build();
		System.out.println(vo);
		MatchingVO saveResult = mRepo.save(vo);
		return saveResult.getM_id() + "OK";
	}
	
	@GetMapping(value = "/matching/test")
	public String aa() {
		
		return "/matching/NewFile";
	}
}
