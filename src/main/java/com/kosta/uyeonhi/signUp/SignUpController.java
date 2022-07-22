package com.kosta.uyeonhi.signUp;





import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.uyeonhi.*;
import com.kosta.uyeonhi.security.MemberService;

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
	MemberService mService;
	private String uploadPath = "/user";
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
	@PostMapping("/signUp4-1")
	public void uMyinfo(String uname, String uid, String upassword, String unick) {
		signUpInfo.put("uname", uname);
		signUpInfo.put("uid", uid);
		signUpInfo.put("upassword", upassword);
		signUpInfo.put("unick", unick);
		log.info(signUpInfo.toString());
		
	}
	@GetMapping("/signUp4-1")
	public ModelAndView uMyinfo2(ModelAndView mnv) {
		
		Map<String, String> infoMap = new HashMap<>();
		iRepo.findAll().forEach(i->{
			infoMap.put(i.getIdealId()+"i", i.getIdealValue());
		});
		fRepo.findAll().forEach(f->{
			infoMap.put(f.getFavoriteId()+"f", f.getFavoriteValue());
		});
		hRepo.findAll().forEach(h->{
			infoMap.put(h.getHobbyId()+"h", h.getHobbyValue());
		});
		mnv.addObject("infoMap", infoMap);
		mnv.addObject("nick",signUpInfo.get("unick"));
		mnv.setViewName("signUp/signUp4-1");
		return mnv;
	}
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
	public void uSignUpFinal(Date birth,String hogam,String mbti,String gender,MultipartFile[] profile) throws IllegalStateException, IOException {
		log.info(hogam);
		log.info(mbti);
		log.info(gender);
		log.info(profile[0].getContentType());
		if(hogam.equals("hogam")) {
			signUpInfo.put("hogam", "hogam");
		}else {
			signUpInfo.put("hogam", "uyeon");
		}
		signUpInfo.put("mbti", mbti);
		signUpInfo.put("gender", gender);
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
		for(MultipartFile p: profile) {
		
	        if(p.getContentType().startsWith("image") == true){//이미지파일 체크
	        	String originalName = p.getOriginalFilename();//파일명:모든 경로를 포함한 파일이름
		        String fileName = originalName.substring(originalName.lastIndexOf("//")+1);
		       
		        String folderPath = makeFolder();
		        //UUID
		        String uuid = UUID.randomUUID().toString();
		        //저장할 파일 이름 중간에 "_"를 이용하여 구분
		        String saveName = uploadPath + File.separator + folderPath +File.separator + uuid + "_" + fileName;
		        
		        Path savePath = Paths.get(saveName);
		        try{
		        	p.transferTo(savePath);
		            //uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
		        } catch (IOException e) {
		             e.printStackTrace();
		             //printStackTrace()를 호출하면 로그에 Stack trace가 출력됩니다.
		        }
		        
		       }
	        }
		
		mService.joinUser(user);
		
	}
	 private String makeFolder(){
	      
	      	String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	        //LocalDate를 문자열로 포멧
	        String folderPath = str.replace("/", File.separator);
	        //만약 Data 밑에 exam.jpg라는 파일을 원한다고 할때,
	        //윈도우는 "Data\\"eaxm.jpg", 리눅스는 "Data/exam.jpg"라고 씁니다.
	        //그러나 자바에서는 "Data" +File.separator + "exam.jpg" 라고 쓰면 됩니다.
	        
	        //make folder ==================
	        File uploadPathFoler = new File(uploadPath, folderPath);
	        //File newFile= new File(dir,"파일명");
	        //->부모 디렉토리를 파라미터로 인스턴스 생성
	        
	        if(uploadPathFoler.exists() == false){
		        uploadPathFoler.mkdirs();
	            //만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미입니다.
	            //mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
				//mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
	           }
	           return folderPath;
	      }
	      
}
