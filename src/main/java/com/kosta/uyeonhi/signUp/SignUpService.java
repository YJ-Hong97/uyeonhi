package com.kosta.uyeonhi.signUp;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.uyeonhi.email.EmailService;
import com.kosta.uyeonhi.email.EmailTokenService;
import com.kosta.uyeonhi.fileUpload.EmptyFileException;
import com.kosta.uyeonhi.fileUpload.FileUploadFailedException;
import com.kosta.uyeonhi.fileUpload.UploadS3Service;
import com.kosta.uyeonhi.security.MemberService;

@Service
public class SignUpService {
	public SignUpInfo getSignUpInfo() {
		return signUpInfo;
	}
	public void setSignUpInfo(SignUpInfo signUpInfo) {
		this.signUpInfo = signUpInfo;
	}
	SignUpInfo signUpInfo = new SignUpInfo();
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
	
	public void addPhone(String phone) {
		signUpInfo.setPhone(phone);
	}
	public void addEmail(String email) {
		signUpInfo.setEmail(email);
	}
	public boolean validTestId(String uid) {
		return uRepo.existsById(uid);
	}
	public boolean validTestNick(String nickname) {
		return uRepo.existsByNickname(nickname);
	}
	public boolean validEmail(String email) {
		return uRepo.existsByEmail(email);
	}
	public void addKakao(String kakao) {
		signUpInfo.setKakao(kakao);
	}
	public void addUid(String uid) {
		signUpInfo.setUid(uid);
	}
	public void addUname(String uname) {
		signUpInfo.setUname(uname);
	}
	public void addUnick(String unick) {
		signUpInfo.setNickname(unick);
	}
	public void addUpassword(String upassword) {
		signUpInfo.setUpassword(upassword);
	}
	public Map<String,String> settingMfav(){
		Map<String, String> settingMap = new HashMap<>();
		 iRepo.findAll().forEach(i->{
			 settingMap.put(i.getIdealId()+"i", i.getIdealValue());
		});
		 fRepo.findAll().forEach(f->{ 
			 settingMap.put(f.getFavoriteId()+"f",f.getFavoriteValue()); 
		}); 
		 hRepo.findAll().forEach(h->{
			 settingMap.put(h.getHobbyId()+"h", h.getHobbyValue()); 
		});
		 return settingMap;
	}
	public void addMInfo(String[] mInfo) {
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
					signUpInfo.getMfList().add(f.getFavoriteId());
				});
				
			}else if(type.equals("h")) {
				hRepo.findById(Long.parseLong(value)).ifPresent(h->{
					signUpInfo.getMhList().add(h.getHobbyId());
				});
				
			}else if(type.equals("i")) {
				iRepo.findById(Long.parseLong(value)).ifPresent(i->{
					signUpInfo.getMiList().add(i.getIdealId());
				});
			}
		}
	}
	public Map<Long, String> idealInfo(){
		Map<Long, String> idealMap = new HashMap<>();
		iRepo.findAll().forEach(i->{
			idealMap.put(i.getIdealId(), i.getIdealValue());
		});
		return idealMap;
	}
	public void addIdeal(String[] idealArr) {
		for(String i:idealArr) {
			signUpInfo.getiList().add(Long.parseLong(i));
		}
	}
	public Map<Long,String> hobbyInfo(){
		Map<Long, String> hobbyMap = new HashMap<>();
		hRepo.findAll().forEach(h->{
			hobbyMap.put(h.getHobbyId(), h.getHobbyValue());
		});
		return hobbyMap;
	}
	public void addHobby(String[] hobbyArr) {
		for(String h:hobbyArr) {
			signUpInfo.gethList().add(Long.parseLong(h));
		}
	}
	public Map<Long, String> FavInfo(){
		Map<Long, String> favMap = new HashMap<>();
		fRepo.findAll().forEach(f->{
			favMap.put(f.getFavoriteId(), f.getFavoriteValue());
		});
		return favMap;
	}
	public void addFav(String[] favArr) {
		for(String f:favArr) {
			signUpInfo.getfList().add(Long.parseLong(f));
		}
	}
	public UserVO addFainal(Date birth,String hogam,String mbti,String gender, MultipartFile[] profile) throws EmptyFileException, FileUploadFailedException {
		signUpInfo.setBirth(birth);
		signUpInfo.setHogam(hogam.equals("hogam")? "hogam":"uyeon");
		signUpInfo.setMbti(mbti);
		signUpInfo.setGender(gender.equals("male")? Gender.MALE:Gender.FEMALE);
		
		UserVO user = UserVO.builder()
				.birth(signUpInfo.getBirth())
				.email(signUpInfo.getEmail())
				.gender(signUpInfo.getGender())
				.id(signUpInfo.getUid())
				.machingConfirm(signUpInfo.getHogam())
				.name(signUpInfo.getUname())
				.nickname(signUpInfo.getNickname())
				.password(signUpInfo.getUpassword())
				.phone(signUpInfo.getPhone())
				.kakao(signUpInfo.getKakao())
				.build();
		
		ArrayList<String> files = uploadService.uploadFile(profile);
		List<ProfileVO> profiless = new ArrayList<>();
		 for(int i = 0; i<files.size(); i++) {
			 if(i==0) {
				 ProfileVO profileVO = ProfileVO.builder()
						 .fileName(files.get(i))
						 .user(user)
						 .type(ProfileType.MAIN)
						 .build();
				 profiless.add(profileVO);
			 }else {
				 ProfileVO profileVO = ProfileVO.builder()
						  .fileName(files.get(i)) 
						  .user(user) 
						  .build(); 
				 profiless.add(profileVO);
			 }
			
					 
		 }
		 
		 user.setProfile(profiless);
		 mService.joinUser(user);
		 return user;
	}
	public void insertmInfo(UserVO user) {
		signUpInfo.getMfList().forEach(mf->{
			FavoriteMenuVO favoritemenu = fRepo.findById(mf).get();
			MFavoriteVO favorite = MFavoriteVO.builder()
					.user(user)
					.favorite(favoritemenu)
					.build();
			mfRepo.save(favorite);
		});
		signUpInfo.getMhList().forEach(mh->{
			HobbyMenuVO hobbymenu = hRepo.findById(mh).get();
			MHobbyVO hobby = MHobbyVO.builder()
					.user(user)
					.hobby(hobbymenu)
					.build();
			mhRepo.save(hobby);
		});
		signUpInfo.getMiList().forEach(mi->{
			IdealMenuVO idealmenu = iRepo.findById(mi).get();
			MIdealVO ideal = MIdealVO.builder()
					.user(user)
					.ideal(idealmenu)
					.build();
			miRepo.save(ideal);
		});
	}
	public void insertFav(UserVO user) {
		signUpInfo.getfList().forEach(f->{
			FavoriteMenuVO favoritemenu = fRepo.findById(f).get();
			FavoriteVO favorite= FavoriteVO.builder()
					.user(user)
					.favorite(favoritemenu)
					.build();
			favoriteRepository.save(favorite);
		});
		signUpInfo.gethList().forEach(h->{
			HobbyMenuVO hobbymenu = hRepo.findById(h).get();
			HobbyVO hobby= HobbyVO.builder()
					.user(user)
					.hobby(hobbymenu)
					.build();
		hobbyRepository.save(hobby);
		});
		signUpInfo.getiList().forEach(i->{
			IdealMenuVO idealmenu = iRepo.findById(i).get();
			IdealTypeVO ideal= IdealTypeVO.builder()
					.user(user)
					.ideal(idealmenu)
					.build();
			idealRepository.save(ideal);
		});
	}
}
