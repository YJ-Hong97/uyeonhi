package com.kosta.uyeonhi.matching;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.kosta.uyeonhi.signUp.UserVO;

@Controller
public class MatchingController {

	@Autowired
	MatchingRepository mRepo;
	
	@PostMapping(value = "")
	public void matchingRequest(String m_id , HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		MatchingVO vo = new MatchingVO();
		/*
		 * vo.setId(session.getAttribute("user")); vo.getTarget(user); vo.setId(user);
		 */
	}
}
