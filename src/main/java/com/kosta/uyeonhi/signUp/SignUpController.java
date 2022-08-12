package com.kosta.uyeonhi.signUp;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.uyeonhi.email.EmailService;
import com.kosta.uyeonhi.email.EmailTokenService;
import com.kosta.uyeonhi.fileUpload.EmptyFileException;
import com.kosta.uyeonhi.fileUpload.FileUploadFailedException;
import com.kosta.uyeonhi.fileUpload.UploadS3Service;
import com.kosta.uyeonhi.security.MemberService;

import lombok.extern.java.Log;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
//http://aaaaaaaaa/hello.do/100
//http://aaaaaaaaa/hello.do?id=100
@Log
@RestController
@RequestMapping("/uyeonhi/*")
public class SignUpController {
	@Autowired
	MemberService mService;
	@Autowired
	EmailService eService;
	@Autowired
	EmailTokenService tService;
	@Autowired
	UploadS3Service uploadService;
	@Autowired
	SignUpService signUpService;
	
	@GetMapping("/signUp")
	public ModelAndView uSignUp1(ModelAndView mnv) {
		mnv.setViewName("signUp/signUp1");
		return mnv;
	}
	@RequestMapping("/signUp2")
	public ModelAndView uSignUp2(ModelAndView mnv) {
		mnv.addObject("kakao",false);
		mnv.setViewName("signUp/signUp2");
		return mnv;
	}
	@PostMapping("/signUp3/{phone}")
	public String uSignUp3(@PathVariable String phone) {
		log.info(phone);
		signUpService.addPhone(phone);
		
		return "s";
	}
	@GetMapping("/signUp3")
	public ModelAndView uSignUp3(ModelAndView mnv) {
		mnv.addObject("kakao",false);
		mnv.setViewName("signUp/signUp3");
		return mnv;
	}
	@PostMapping("/signUpp/{phone}")
	public String phonecheck(@PathVariable("phone") String phone, HttpSession session) {
		String api_key = "NCSMFWG3LNXFCMBL";
		String api_secret = "6FXVU1QJCBFUQC3CSGPLJ9K1MXUDLL1G";
		Message coolsms = new Message(api_key, api_secret);
		
		int checkNum = (int)(Math.random()*10000);
		// 4 params(to, from, type, text) are mandatory. must be filled
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("to", phone);	// 수신전화번호
		params.put("from", "01041110265");	// 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
		params.put("type", "SMS");
		params.put("text", String.valueOf(checkNum));
		params.put("app_version", "test app 1.2"); // application name and version
		
		try {
		  JSONObject obj = (JSONObject) coolsms.send(params);
		  System.out.println(obj.toString());
		} catch (CoolsmsException e) {
		  System.out.println(e.getMessage());
		  System.out.println(e.getCode());
		}
		
		return String.valueOf(checkNum);
	}
	@PostMapping("/signUp4/{email}")
	public void uSignUp4(@PathVariable("email")String email) {
		signUpService.addEmail(email);
		
	}
	@GetMapping("/signUp4")
	public ModelAndView uSignUp4(ModelAndView mnv) {
		mnv.addObject("kakao",false);
		mnv.setViewName("signUp/signUp4");
		return mnv;
	}
	
	@PostMapping("/validTestId/{uid}")
	public String validTestId(@PathVariable String uid) {
		
		boolean result = signUpService.validTestId(uid);
		
		if(result) {
			return "fail";
		}else {
			return "success";
		}
	}
	@PostMapping("/validTestNick/{unick}")
	public String validTestNick(@PathVariable String unick) {
		boolean result= signUpService.validTestNick(unick);
		if(result) {
			return "fail";
		}else {
			return "success";
		}
	}
	@PostMapping("/validEmail/{email}")
	public boolean validEmail(@PathVariable String email) throws MessagingException {
		boolean result = signUpService.validEmail(email);
		if(result) {
			tService.createEmailToken(email);
			return result;
		}else {
			tService.createEmailToken(email);
		}
		return result;
	}
	@GetMapping("/mailing")
	public ModelAndView mailing(ModelAndView mnv) {
		mnv.addObject("kakao",false);
		mnv.setViewName("signUp/signUpMail");
		return mnv;
	}
	@GetMapping("/validEmail/{token}/{email}")
	public ModelAndView validEmail(@PathVariable String token,@PathVariable String email,ModelAndView mnv) {
		eService.verifyEmail(token);
		mnv.setViewName("signUp/signUp4");
		signUpService.addEmail(email);
		signUpService.addKakao("false");
		signUpService.addUid(null);
		mnv.addObject("signUpInfo",signUpService.getSignUpInfo());
		return mnv;
		
	}
	@RequestMapping(value = "/signUp4-1", method = RequestMethod.POST)
	public void uMyinfo(String uname, String uid, String upassword, String unick) {
		signUpService.addUname(uname);
		if(uid != null) {
			signUpService.addUid(uid);
		}
		signUpService.addUpassword(upassword);
		signUpService.addUnick(unick);
		
	}

	
	 @GetMapping("/signUp4-1") public ModelAndView uMyinfo2(ModelAndView mnv) {
		 Map<String, String> infoMap = signUpService.settingMfav();
		mnv.addObject("infoMap", infoMap);
		mnv.addObject("nick",signUpService.getSignUpInfo().getNickname());
	 
		mnv.setViewName("signUp/signUp4-1"); return mnv;
	}
	 
	@PostMapping("/signUp5")
	public void uSignUp5(String[] mInfo) {
		signUpService.addMInfo(mInfo);
	}
	@GetMapping("/signUp5")
	public ModelAndView uSignUp5(ModelAndView mnv) {
		Map<Long, String> idealMap =signUpService.idealInfo();
		mnv.addObject("idealMap", idealMap);
		mnv.setViewName("signUp/signUp5");
		return mnv;
	}
	@PostMapping("/signUp6")
	public void uSignUp6(String[] idealArr) {
		signUpService.addIdeal(idealArr);
	}
	@GetMapping("/signUp6")
	public ModelAndView uSignUp6(ModelAndView mnv) {
		Map<Long, String> hobbyMap =signUpService.hobbyInfo();
		mnv.addObject("hobbyMap", hobbyMap);
		mnv.setViewName("signUp/signUp6");
		return mnv;
	}
	@PostMapping("/signUp7")
	public void uSignUp7(String[] hobbyArr) {
		signUpService.addHobby(hobbyArr);
	}
	@GetMapping("/signUp7")
	public ModelAndView uSignUp7(ModelAndView mnv) {
		Map<Long, String> favMap = signUpService.FavInfo();
		mnv.addObject("favMap", favMap);
		mnv.setViewName("signUp/signUp7");
		return mnv;
	}
	@PostMapping("/signUp8")
	public void uSignUp8(String[] favArr) {
		signUpService.addFav(favArr);
	}
	@GetMapping("/signUp8")
	public ModelAndView uSignUp8(ModelAndView mnv) {
		mnv.setViewName("signUp/signUp8");
		return mnv;
	}

	@PostMapping("/signUpFinal")
	public ModelAndView uSignUpFinal(Date birth,String hogam,String mbti,String gender,MultipartFile[] profile,ModelAndView mnv) throws IllegalStateException, IOException, EmptyFileException, FileUploadFailedException{
		
		
		UserVO user = signUpService.addFainal(birth, hogam, mbti, gender,profile);
		
		
		//내 소개 저장
		signUpService.insertmInfo(user);
		//취향 저장
		signUpService.insertFav(user);
		mnv.setViewName("redirect:/auth/login");
		return mnv;
	}
	
	@RequestMapping("/kakao/signUp2")
	public ModelAndView kuSignUp2(ModelAndView mnv) {
		mnv.addObject("kakao",true);
		mnv.setViewName("signUp/signUp2");
		return mnv;
	}
	@PostMapping("/kakao/signUp3/{phone}")
	public String kSignUp3(@PathVariable String phone) {
		
		signUpService.addPhone(phone);
		return "s";
	}
	@GetMapping("/kakao/signUp3/{email}/{kakao}")
	public ModelAndView kSignUp3(ModelAndView mnv,@PathVariable("email")String email,@PathVariable("kakao")String kakao) {
		signUpService.addKakao(kakao);
		signUpService.addEmail(email);
		signUpService.addUid(email);
		mnv.addObject("kakao",true);
		mnv.addObject("signUpInfo",signUpService.getSignUpInfo());
		mnv.setViewName("signUp/signUp4");
		return mnv;
	}
	
	
	
	
}
