package com.kosta.uyeonhi.mypage;

import java.io.IOException;
import java.lang.ProcessHandle.Info;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.uyeonhi.chat.ChatRoomDTO;
import com.kosta.uyeonhi.chat.ChatRoomRepository;
import com.kosta.uyeonhi.follow.Follow;
import com.kosta.uyeonhi.follow.FollowRepository;
import com.kosta.uyeonhi.matching.MatchingGradeRepository;
import com.kosta.uyeonhi.matching.MatchingRepository;
import com.kosta.uyeonhi.matching.MatchingVO;
import com.kosta.uyeonhi.matching.matchingGrade;
import com.kosta.uyeonhi.security.MemberService;
import com.kosta.uyeonhi.signUp.FavoriteMenuRepository;
import com.kosta.uyeonhi.signUp.FavoriteMenuVO;
import com.kosta.uyeonhi.signUp.FavoriteRepository;
import com.kosta.uyeonhi.signUp.FavoriteVO;
import com.kosta.uyeonhi.signUp.HobbyMenuRepository;
import com.kosta.uyeonhi.signUp.HobbyMenuVO;
import com.kosta.uyeonhi.signUp.HobbyRepository;
import com.kosta.uyeonhi.signUp.HobbyVO;
import com.kosta.uyeonhi.signUp.IdealMenuRepository;
import com.kosta.uyeonhi.signUp.IdealMenuVO;
import com.kosta.uyeonhi.signUp.IdealRepository;
import com.kosta.uyeonhi.signUp.IdealTypeVO;
import com.kosta.uyeonhi.signUp.MFavoriteRepository;
import com.kosta.uyeonhi.signUp.MFavoriteVO;
import com.kosta.uyeonhi.signUp.MHobbyRepository;
import com.kosta.uyeonhi.signUp.MHobbyVO;
import com.kosta.uyeonhi.signUp.MIdealRepository;
import com.kosta.uyeonhi.signUp.MIdealVO;
import com.kosta.uyeonhi.signUp.ProfileRepository;
import com.kosta.uyeonhi.signUp.ProfileType;
import com.kosta.uyeonhi.signUp.ProfileVO;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;
import com.kosta.uyeonhi.sns.BoardRepository;
import com.kosta.uyeonhi.util.PageMaker;
import com.kosta.uyeonhi.util.PageVO;

import groovyjarjarantlr4.v4.parse.ANTLRParser.prequelConstruct_return;
import lombok.extern.log4j.Log4j2;
@Log4j2
@RestController
public class MyPageController {
	@Autowired
	NotToMeetRepository nRepo;
	@Autowired
	InquiryRepository iRepo;
	@Autowired 
	MHobbyRepository mhRepo;
	@Autowired
	MFavoriteRepository mfRepo;
	@Autowired
	MIdealRepository miRepo;
	@Autowired
	HobbyMenuRepository hMenuRepo;
	@Autowired
	FavoriteMenuRepository fMenuRepo;
	@Autowired
	IdealMenuRepository iMenuRepo;
	@Autowired
	HobbyRepository hRepo;
	@Autowired
	FavoriteRepository fRepo;
	@Autowired
	IdealRepository idealRepo;
	@Autowired
	MemberService memberService;
	@Autowired
	ChattingRoomRepository chatRoomRepo;
	@Autowired
	ChattingUsersRepository chatUserRepo;
	@Autowired
	ProfileRepository profileRepo;
	@Autowired
	FollowRepository followRepo;
	@Autowired
	MemberService mservice;
	@Autowired
	FollowRepository fRepos;
	
	@Autowired
	MatchingRepository mRepo;
	@Autowired
	BoardRepository boardRepo;
	@Autowired
	UserRepository uRepo;
	@Autowired
	MatchingGradeRepository gradeRepo;
	
	@Autowired
	ChatRoomRepository rrrepo;
	
	@GetMapping("/myPage/{mid}")
	public ModelAndView myPage(@PathVariable String mid, ModelAndView model, HttpSession session) {
		System.out.println(mid);
		UserVO user = (UserVO) session.getAttribute("user");
		mservice.getUserProfile(mid, model);
		Follow follow = fRepos.checkFollow(mid, user.getId());
		ProfileVO profile = profileRepo.findByUserAndType(mservice.getOther(mid), ProfileType.MAIN);
		MatchingVO mcheck = mRepo.matcheck(user.getId(), mid);
		List<Board> listboard = boardRepo.findByWriter(uRepo.findById(mid).get());
		
		int truemcheck = -2;
		if(mcheck == null) {
			truemcheck = -1;
		}
		
		else {
			if(mcheck.getMconfirm() == 1) {
				truemcheck = 1;
			}
			else {
				if(mcheck.getTarget().getId().equals(user.getId())) {
					truemcheck = -1;
				}
				else truemcheck = mcheck.getMconfirm();
			}
		}
		
		model.addObject("listboard", listboard != null ? listboard : 0);
		System.out.println(listboard);
		model.addObject("mcheck", truemcheck);
		model.addObject("isFollow", follow != null ? 1 : 0);
		model.addObject("follower", followRepo.countFollower(mid));
		model.addObject("following", followRepo.countFollowing(mid));
		model.addObject("profile",profile);
		model.setViewName("/auth/myPage");
		return model;
	}
	
	@GetMapping("/setting")
	public ModelAndView settingModal(ModelAndView mnv,HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		ProfileVO mainPro = profileRepo.findByUserAndType(user, ProfileType.MAIN);
		
		mnv.addObject("user",user);
		mnv.addObject("profile",mainPro);
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
		UserVO buser = uRepo.findByPhone(phone);
		nRepo.deleteBlock(user, phone);
		matchingGrade grade = gradeRepo.findByUserAndTarget(user, buser);
		grade.setBlock(0);
		gradeRepo.save(grade);
		
	}
	@GetMapping("/inquiry")
	public ModelAndView inquiry(ModelAndView mnv, HttpServletRequest request,PageVO pageVO) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		mnv.addObject("user",user);
		mnv.setViewName("/mypage/inquiry");
		
		ProfileVO profile = profileRepo.findByUserAndType(user, ProfileType.MAIN);
		mnv.addObject("profile",profile);
		
		Pageable page = pageVO.makePageable(0,0,"inquiryId");
		Page<InquiryVO> result = iRepo.findAll(iRepo.makePredicate(null, null), page);
		
		mnv.addObject("result",new PageMaker<>(result));
		return mnv;
		
	}
	@GetMapping("/inquiryPage/{pageNum}")
	public ModelAndView inquiryPage(ModelAndView mnv, HttpServletRequest request, PageVO pageVO,@PathVariable("pageNum") int pagenum) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		mnv.addObject("user",user);
		mnv.setViewName("/mypage/inquiry");
		
		ProfileVO profile = profileRepo.findByUserAndType(user, ProfileType.MAIN);
		mnv.addObject("profile",profile);
		
		Pageable page = pageVO.makePageable(0,pagenum,"inquiryId");
		Page<InquiryVO> result = iRepo.findAll(iRepo.makePredicate(null, null), page);
		
		mnv.addObject("result",new PageMaker<>(result));
		return mnv;
	}
	@GetMapping("/newInquiry")
	public ModelAndView newInquiry(ModelAndView mnv, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		mnv.addObject("user",user);
		mnv.setViewName("/mypage/newInquiry");
		
		ProfileVO profile = profileRepo.findByUserAndType(user, ProfileType.MAIN);
		mnv.addObject("profile",profile);
		return mnv;
		
	}
	@PostMapping("/newInquiry")
	public void newinquiry(HttpServletResponse response,ModelAndView mnv, HttpServletRequest request,@RequestParam("title")String title, @RequestParam("content")String content ) throws IOException {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		InquiryVO inquiry = InquiryVO.builder()
				.title(title)
				.content(content)
				.user(user)
				.build();
		iRepo.save(inquiry);
		response.sendRedirect("/inquiry");
		
	}
	@PostMapping("/detailInquiry")
	public InquiryVO detailInquiry(long inquiryId,HttpServletRequest request){
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		InquiryVO inquiry = iRepo.findById(inquiryId).get();
		if(inquiry.getUser().getId().equals(user.getId())) {
			
			return inquiry;
		}else {
			return null;
		}
		
	}
	@PostMapping("/updateInquiry")
	public void updateInquiry(long inquiryId, String title, String content, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		InquiryVO inquiry = InquiryVO.builder()
				.inquiryId(inquiryId)
				.title(title)
				.content(content)
				.user(user)
				.build();
		iRepo.save(inquiry);
	}
	@PostMapping("/deleteInquiry")
	public void deleteInquiry(long inquiryId) {
		iRepo.deleteById(inquiryId);
	}
	@GetMapping("/aboutMe")
	public ModelAndView aboutMe(HttpServletRequest request,ModelAndView mnv) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		
		Map<String,String> settingMap =  new HashMap<>();
		fMenuRepo.findAll().forEach(f->{
			settingMap.put(f.getFavoriteId()+"f", f.getFavoriteValue());
		});
		hMenuRepo.findAll().forEach(h->{
			settingMap.put(h.getHobbyId()+"h", h.getHobbyValue());
		});
		iMenuRepo.findAll().forEach(i->{
			settingMap.put(i.getIdealId()+"i", i.getIdealValue());
		});
		
		List<String> myList = new ArrayList<>();
		mfRepo.findByUser(user).forEach(mf->{
			myList.add(mf.getFavorite().getFavoriteId()+"f");
		});
		mhRepo.findByUser(user).forEach(mh->{
			myList.add(mh.getHobby().getHobbyId()+"h");
		});
		miRepo.findByUser(user).forEach(mi->{
			myList.add(mi.getIdeal().getIdealId()+"i");
		});
		
		ProfileVO profile = profileRepo.findByUserAndType(user, ProfileType.MAIN);
		mnv.addObject("profile",profile);
		
		mnv.addObject("settingMap",settingMap);
		mnv.addObject("myList",myList);
		mnv.addObject("user",user);
		
		mnv.setViewName("/mypage/aboutMe");
		return mnv;
	}
	@PostMapping("/aboutMe")
	public void aboutMe(HttpServletRequest request, String[] mInfo) {
		log.info(Arrays.toString(mInfo));
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		mfRepo.deleteByUser(user);
		mhRepo.deleteByUser(user);
		miRepo.deleteByUser(user);
		for(int i = 0; i<mInfo.length; i++) {
			if(mInfo[i].contains("h")) {
				Long hId = Long.parseLong(mInfo[i].substring(0,mInfo[i].length()-1));
				HobbyMenuVO hMenuVO = hMenuRepo.findById(hId).get();
				MHobbyVO hobby = MHobbyVO.builder()
						.user(user)
						.hobby(hMenuVO)
						.build();
				mhRepo.save(hobby);
			}else if(mInfo[i].contains("f")) {
				Long fId = Long.parseLong(mInfo[i].substring(0,mInfo[i].length()-1));
				FavoriteMenuVO favoriteMenuVO = fMenuRepo.findById(fId).get();
				MFavoriteVO favorite = MFavoriteVO.builder()
						.user(user)
						.favorite(favoriteMenuVO)
						.build();
				mfRepo.save(favorite);
				
			}else if(mInfo[i].contains("i")){
				Long iId = Long.parseLong(mInfo[i].substring(0,mInfo[i].length()-1));
				IdealMenuVO idealMenuVO = iMenuRepo.findById(iId).get();
				MIdealVO ideal = MIdealVO.builder()
						.user(user)
						.ideal(idealMenuVO)
						.build();
				miRepo.save(ideal);
			}
			
		}
	}
	@GetMapping("/aboutYoui")
	public ModelAndView aboutYoui(ModelAndView mnv,HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		
		Map<Long,String> settingMap =  new HashMap<>();
		iMenuRepo.findAll().forEach(im->{
			settingMap.put(im.getIdealId(), im.getIdealValue());
		});
		
		List<Long> myList = new ArrayList<>();
		idealRepo.findByuserId(user.getId()).forEach(i->{
			myList.add(i.getIdeal().getIdealId());
		});
		
		ProfileVO profile = profileRepo.findByUserAndType(user, ProfileType.MAIN);
		mnv.addObject("profile",profile);
		
		mnv.addObject("settingMap",settingMap);
		mnv.addObject("myList",myList);
		mnv.addObject("user",user);
		mnv.setViewName("/mypage/aboutYoui");
		
		return mnv;
	}
	@PostMapping("/aboutYoui")
	public void aboutYoui(HttpServletRequest request, Long[] idealArr) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		idealRepo.deleteByuserId(user.getId());
		for(int i = 0 ; i<idealArr.length; i++) {
			IdealMenuVO idealMenuVO = iMenuRepo.findById(idealArr[i]).get();
			IdealTypeVO ideal = IdealTypeVO.builder()
					.user(user)
					.ideal(idealMenuVO)
					.build();
			idealRepo.save(ideal);
		}
	}
	@GetMapping("/aboutYouf")
	public ModelAndView aboutYouf(ModelAndView mnv, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		
		Map<Long,String> settingMap =  new HashMap<>();
		fMenuRepo.findAll().forEach(fm->{
			settingMap.put(fm.getFavoriteId(), fm.getFavoriteValue());
		});
		
		List<Long> myList = new ArrayList<>();
		fRepo.findByuserId(user.getId()).forEach(f->{
			myList.add(f.getFavorite().getFavoriteId());
		});
		
		ProfileVO profile = profileRepo.findByUserAndType(user, ProfileType.MAIN);
		mnv.addObject("profile",profile);
		
		mnv.addObject("settingMap",settingMap);
		mnv.addObject("myList",myList);
		mnv.addObject("user",user);
		mnv.setViewName("/mypage/aboutYouf");
		
		return mnv;
	}
	@PostMapping("/aboutYouf")
	public void aboutYouf(HttpServletRequest request,Long[] favoriteArr) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		fRepo.deleteByUserId(user.getId());
		for(int i = 0 ; i<favoriteArr.length; i++) {
			FavoriteMenuVO favoriteMenuVO = fMenuRepo.findById(favoriteArr[i]).get();
			FavoriteVO favorite = FavoriteVO.builder()
					.user(user)
					.favorite(favoriteMenuVO)
					.build();
			fRepo.save(favorite);
		}
	}
	@GetMapping("/aboutYouh")
	public ModelAndView aboutYouh(ModelAndView mnv, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		
		Map<Long,String> settingMap =  new HashMap<>();
		hMenuRepo.findAll().forEach(hm->{
			settingMap.put(hm.getHobbyId(), hm.getHobbyValue());
		});
		
		List<Long> myList = new ArrayList<>();
		hRepo.findByuserId(user.getId()).forEach(h->{
			myList.add(h.getHobby().getHobbyId());
		});
		
		ProfileVO profile = profileRepo.findByUserAndType(user, ProfileType.MAIN);
		mnv.addObject("profile",profile);
		mnv.addObject("settingMap",settingMap);
		mnv.addObject("myList",myList);
		mnv.addObject("user",user);
		mnv.setViewName("/mypage/aboutYouh");
		
		return mnv;
	}
	@PostMapping("/aboutYouh")
	public void aboutYouh(HttpServletRequest request,Long[] hobbyArr) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		hRepo.deleteByUserId(user.getId());
		for(int i = 0 ; i<hobbyArr.length; i++) {
			HobbyMenuVO hobbyMenuVO = hMenuRepo.findById(hobbyArr[i]).get();
			HobbyVO hobby = HobbyVO.builder()
					.user(user)
					.hobby(hobbyMenuVO)
					.build();
			hRepo.save(hobby);
		}
	}
	@PostMapping("/updatePW")
	public void updatePW(String password, HttpSession session) {
		 
		UserVO user = (UserVO) session.getAttribute("user");
		user.setPassword(password);
		memberService.joinUser(user);
		
	}
	@PostMapping("/daccount")
	public boolean daccount(HttpSession session,String password) {
		UserVO user = (UserVO)session.getAttribute("user");
		boolean result = memberService.daccout(user, password);
		return result;
	}
	@GetMapping("/loadChat")
	public ModelAndView loadChat(ModelAndView mnv,HttpSession session) {
		UserVO user = (UserVO)session.getAttribute("user");
		Map<ChattingRoomVO,List<ProfileVO>> roomMap = new HashMap<>();
		List<ChattingRoomVO> rooms = chatRoomRepo.findByUser(user);
		List<ChattingUsersVO> cusers = chatUserRepo.findByUser(user);
		List<ChatRoomDTO> chatrooms = rrrepo.findByTargetOrId(user.getId());
		for(ChattingUsersVO c: cusers) {
			ChattingRoomVO room = chatRoomRepo.findById(c.getRoom().getRoomNo()).get();
			List<ChattingUsersVO> users = chatUserRepo.findByRoom(room);
			List<ProfileVO> profiles = new ArrayList<>();
			for(int i= 0; i<users.size(); i++) {
				ProfileVO p = profileRepo.findByUserAndType(users.get(i).getUser(), ProfileType.MAIN);
				profiles.add(p);
			}
			roomMap.put(room, profiles);
		}
		
		
		ProfileVO profile = profileRepo.findByUserAndType(user, ProfileType.MAIN);
		mnv.addObject("chatrooms", chatrooms);
		mnv.addObject("profile",profile);
		mnv.addObject("roomMap",roomMap);
		mnv.setViewName("/mypage/chatRoom");
		mnv.addObject("user",user);
		return mnv;
	}
	
}
