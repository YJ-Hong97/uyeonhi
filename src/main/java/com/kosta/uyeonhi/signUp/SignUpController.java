package com.kosta.uyeonhi.signUp;





import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.uyeonhi.*;

import antlr.TokenWithIndex;
import lombok.extern.java.Log;
//http://aaaaaaaaa/hello.do/100
//http://aaaaaaaaa/hello.do?id=100
@Log
@RestController
@RequestMapping("/uyeonhi/*")
public class SignUpController {
	@Autowired
	UserRepository uRepo;
	@Autowired
	ManagerRepository mRepo;
	@Autowired
	IdealMenuRepository iRepo;
	
	Map<String, String> signUpInfo = new HashMap<>();
	
	@GetMapping("/signUp")
	public ModelAndView uSignUp1(ModelAndView mnv) {
		mnv.setViewName("signUp/signUp1");
		return mnv;
	}
	@RequestMapping("/signUp2")
	public ModelAndView uSignUp2(ModelAndView mnv) {
		mnv.setViewName("signUp/signUp2");
		return mnv;
	}
	@PostMapping("/signUp3/{phone}")
	public String uSignUp3(@PathVariable String phone) {
		
		log.info(phone);
		signUpInfo.put("phone", phone);
		return "s";
	}
	@GetMapping("/signUp3")
	public ModelAndView uSignUp3(ModelAndView mnv) {
		mnv.setViewName("signUp/signUp3");
		return mnv;
	}
	@PostMapping("/signUp4/{email}")
	public void uSignUp4(@PathVariable("email")String email) {
		log.info(email);
		signUpInfo.put("email", email);
		log.info(signUpInfo.toString());
	}
	@GetMapping("/signUp4")
	public ModelAndView uSignUp4(ModelAndView mnv) {
		mnv.setViewName("signUp/signUp4");
		return mnv;
	}
	@PostMapping("/validTestId")
	public String validTestId( String uid) {
		boolean result = true;
		result = uRepo.existsById(uid);
		result = mRepo.existsById(uid);
		if(result) {
			return "fail";
		}else {
			return "success";
		}
	}
	@PostMapping("/validTestNick")
	public String validTestNick(String unick) {
		boolean result = true;
		result = uRepo.existsByNickname(unick);
		if(result) {
			return "fail";
		}else {
			return "success";
		}
	}
	@PostMapping("/validEmail/{email}")
	public boolean validEmail(@PathVariable String email) {
		boolean result = uRepo.existsByEmail(email);
		return result;
	}
	@PostMapping("/signUp5")
	public void uSignUp5(String uname, String uid, String upassword, String unick) {
		
		signUpInfo.put("uname", uname);
		signUpInfo.put("uid", uid);
		signUpInfo.put("upassword", upassword);
		signUpInfo.put("unick", unick);
		log.info(signUpInfo.toString());
		
	}
	@GetMapping("/signUp5")
	public ModelAndView uSignUp5(ModelAndView mnv) {
		Map<Long, String> idealMap = new HashMap<>();
		iRepo.findAll().forEach(i->{
			idealMap.put(i.getIdealId(), i.getIdealValue());
		});
		mnv.addObject("idealMap", idealMap);
		mnv.setViewName("signUp/signUp5");
		return mnv;
	}
}
