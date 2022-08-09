package com.kosta.uyeonhi.signUp;









import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

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
	@Autowired
	FavoriteMenuRepository fRepo;
	@Autowired
	HobbyMenuRepository hRepo;
	@Autowired
	MFavoriteRepository mfRepo;
	@Autowired
	MHobbyRepository mhRepo;
	@Autowired
	MIdealRepository miRepo;
	@Autowired
	FavoriteRepository favoriteRepository;
	@Autowired
	HobbyRepository hobbyRepository;
	@Autowired
	IdealRepository idealRepository;
	@Autowired
	MemberService mService;
	@Autowired
	EmailService eService;
	@Autowired
	EmailTokenService tService;
	@Autowired
	UploadS3Service uploadService;
	@Autowired
	ProfileRepository pRepo;
	
	Map<String, String> signUpInfo = new HashMap<>();
	ArrayList<Long> mfList = new ArrayList<>();
	ArrayList<Long> mhList = new ArrayList<>();
	ArrayList<Long> miList = new ArrayList<>();
	ArrayList<Long> fList = new ArrayList<>();
	ArrayList<Long> hList = new ArrayList<>();
	ArrayList<Long> iList = new ArrayList<>();
	
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
	@GetMapping("/signUp4/{signUpInfo}")
	public ModelAndView uSignUp4(ModelAndView mnv,@PathVariable Map<String, String> signUpInfo) {
		this.signUpInfo = signUpInfo;
		log.info(signUpInfo.toString());
		mnv.setViewName("signUp/signUp4");
		return mnv;
	}
	@PostMapping("/validTestId/{uid}")
	public String validTestId(@PathVariable String uid) {
		log.info("아아");
		log.info(uid);
		boolean result = true;
		result = uRepo.existsById(uid);
		result = mRepo.existsById(uid);
		
		if(result) {
			return "fail";
		}else {
			return "success";
		}
	}
	@PostMapping("/validTestNick/{unick}")
	public String validTestNick(@PathVariable String unick) {
		boolean result = true;
		result = uRepo.existsByNickname(unick);
		if(result) {
			return "fail";
		}else {
			return "success";
		}
	}
	@PostMapping("/validEmail/{email}")
	public boolean validEmail(@PathVariable String email) throws MessagingException {
		boolean result = uRepo.existsByEmail(email);
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
		mnv.setViewName("signUp/signUpMail");
		return mnv;
	}
	@GetMapping("/validEmail/{token}/{email}")
	public ModelAndView validEmail(@PathVariable String token,@PathVariable String email,ModelAndView mnv) {
		eService.verifyEmail(token);
		mnv.setViewName("signUp/signUp4");
		signUpInfo.put("email",email);
		log.info(signUpInfo.toString());
		return mnv;
		
	}
	@RequestMapping(value = "/signUp4-1", method = RequestMethod.POST)
	public void uMyinfo(String uname, String uid, String upassword, String unick) {
		signUpInfo.put("uname", uname);
		signUpInfo.put("uid", uid);
		signUpInfo.put("upassword", upassword);
		signUpInfo.put("unick", unick);
		log.info(signUpInfo.toString());
		log.info("4-1post왔다");
		
	}

	
	 @GetMapping("/signUp4-1") public ModelAndView uMyinfo2(ModelAndView mnv) {
	 
	 Map<String, String> infoMap = new HashMap<>(); iRepo.findAll().forEach(i->{
	 infoMap.put(i.getIdealId()+"i", i.getIdealValue()); });
	 fRepo.findAll().forEach(f->{ infoMap.put(f.getFavoriteId()+"f",
	 f.getFavoriteValue()); }); hRepo.findAll().forEach(h->{
	 infoMap.put(h.getHobbyId()+"h", h.getHobbyValue()); });
	 mnv.addObject("infoMap", infoMap);
	 mnv.addObject("nick",signUpInfo.get("unick"));
	 mnv.setViewName("signUp/signUp4-1"); return mnv; }
	 
	@PostMapping("/signUp5")
	public void uSignUp5(String[] mInfo) {
		for(String m : mInfo) {
			String type = null;
			String value = null;
			if(m.length()==3) {
				type = m.substring(2);
				value = m.substring(0,2);
			}else {
				type = m.substring(1);
				value = m.substring(0,1);
			}
			if(type.equals("f")) {
				fRepo.findById(Long.parseLong(value)).ifPresent(f->{
					mfList.add(f.getFavoriteId());
				});
				
			}else if(type.equals("h")) {
				hRepo.findById(Long.parseLong(value)).ifPresent(h->{
					mfList.add(h.getHobbyId());
				});
				
			}else if(type.equals("i")) {
				iRepo.findById(Long.parseLong(value)).ifPresent(i->{
					miList.add(i.getIdealId());
				});
			}
		}
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
	@PostMapping("/signUp6")
	public void uSignUp6(String[] idealArr) {
		for(String i:idealArr) {
			iList.add(Long.parseLong(i));
		}
	}
	@GetMapping("/signUp6")
	public ModelAndView uSignUp6(ModelAndView mnv) {
		Map<Long, String> hobbyMap = new HashMap<>();
		hRepo.findAll().forEach(h->{
			hobbyMap.put(h.getHobbyId(), h.getHobbyValue());
		});
		mnv.addObject("hobbyMap", hobbyMap);
		mnv.setViewName("signUp/signUp6");
		return mnv;
	}
	@PostMapping("/signUp7")
	public void uSignUp7(String[] hobbyArr) {
		for(String h:hobbyArr) {
			hList.add(Long.parseLong(h));
		}
	}
	@GetMapping("/signUp7")
	public ModelAndView uSignUp7(ModelAndView mnv) {
		Map<Long, String> favMap = new HashMap<>();
		fRepo.findAll().forEach(f->{
			favMap.put(f.getFavoriteId(), f.getFavoriteValue());
		});
		mnv.addObject("favMap", favMap);
		mnv.setViewName("signUp/signUp7");
		return mnv;
	}
	@PostMapping("/signUp8")
	public void uSignUp8(String[] favArr) {
		for(String f:favArr) {
			hList.add(Long.parseLong(f));
		}
	}
	@GetMapping("/signUp8")
	public ModelAndView uSignUp8(ModelAndView mnv) {
		
		mnv.setViewName("signUp/signUp8");
		return mnv;
	}

	@PostMapping("/signUpFinal")
	public ModelAndView uSignUpFinal(Date birth,String hogam,String mbti,String gender,MultipartFile[] profile,ModelAndView mnv) throws IllegalStateException, IOException, EmptyFileException, FileUploadFailedException{
		
		if(hogam.equals("hogam")) {
			signUpInfo.put("hogam", "hogam");
		}else {
			signUpInfo.put("hogam", "uyeon");
		}
		signUpInfo.put("mbti", mbti);
		signUpInfo.put("gender", gender);
		ArrayList<String> files = uploadService.uploadFile(profile);
		
		UserVO user = UserVO.builder()
				.birth(birth)
				.email(signUpInfo.get("email"))
				.gender(gender.equals("male")? Gender.MALE: Gender.FEMALE)
				.id(signUpInfo.get("uid"))
				.machingConfirm(hogam.equals("hogam")? "hogam":"uyeon")
				.name(signUpInfo.get("uname"))
				.nickname(signUpInfo.get("unick"))
				.password(signUpInfo.get("upassword"))
				.phone(signUpInfo.get("phone"))
				.build();
		mService.joinUser(user);
		
		 for(int i = 0; i<files.size(); i++) {
			 if(i==0) {
				 ProfileVO profileVO = ProfileVO.builder()
						 .fileName(files.get(i))
						 .user(user)
						 .type(ProfileType.MAIN)
						 .build();
				 pRepo.save(profileVO);
			 }else {
				 ProfileVO profileVO = ProfileVO.builder()
						  .fileName(files.get(i)) 
						  .user(user) 
						  .build(); 
				  pRepo.save(profileVO); 
			 }
			
					 
		 }
		
		//내 소개 저장
		mfList.forEach(mf->{
			MFavoriteVO favorite = MFavoriteVO.builder()
					.user(user)
					.favoriteId(mf)
					.build();
			mfRepo.save(favorite);
		});
		mhList.forEach(mh->{
			MHobbyVO hobby = MHobbyVO.builder()
					.user(user)
					.hobbyId(mh)
					.build();
			mhRepo.save(hobby);
		});
		miList.forEach(mi->{
			MIdealVO ideal = MIdealVO.builder()
					.user(user)
					.idealId(mi)
					.build();
			miRepo.save(ideal);
		});
		//취향 저장
		fList.forEach(f->{
			FavoriteVO favorite= FavoriteVO.builder()
					.user(user)
					.favoriteId(f)
					.build();
			favoriteRepository.save(favorite);
		});
		hList.forEach(h->{
			HobbyVO hobby= HobbyVO.builder()
					.user(user)
					.hobbyId(h)
					.build();
		hobbyRepository.save(hobby);
		});
		iList.forEach(i->{
			IdealTypeVO ideal= IdealTypeVO.builder()
					.user(user)
					.idealId(i)
					.build();
			idealRepository.save(ideal);
		});
		mnv.setViewName("/auth/login");
		return mnv;
	}
	
}
