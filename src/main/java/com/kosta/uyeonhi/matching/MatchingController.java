package com.kosta.uyeonhi.matching;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.uyeonhi.mypage.NotToMeetRepository;
import com.kosta.uyeonhi.mypage.NotToMeetVO;
import com.kosta.uyeonhi.signUp.FavoriteRepository;
import com.kosta.uyeonhi.signUp.FavoriteVO;
import com.kosta.uyeonhi.signUp.Gender;
import com.kosta.uyeonhi.signUp.HobbyRepository;
import com.kosta.uyeonhi.signUp.HobbyVO;
import com.kosta.uyeonhi.signUp.IdealRepository;
import com.kosta.uyeonhi.signUp.IdealTypeVO;
import com.kosta.uyeonhi.signUp.MFavoriteRepository;
import com.kosta.uyeonhi.signUp.MFavoriteVO;
import com.kosta.uyeonhi.signUp.MHobbyRepository;
import com.kosta.uyeonhi.signUp.MHobbyVO;
import com.kosta.uyeonhi.signUp.MIdealRepository;
import com.kosta.uyeonhi.signUp.MIdealVO;
import com.kosta.uyeonhi.signUp.ProfileRepository;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.extern.java.Log;
@Log
@Controller
public class MatchingController {

	@Autowired
	ProfileRepository profileRepo;
	
	@Autowired
	MatchingRepository mRepo;
	@Autowired
	UserRepository uRepo;
	@Autowired
	MFavoriteRepository mfRepo;
	@Autowired
	MHobbyRepository mhRepo;
	@Autowired
	MIdealRepository miRepo;
	@Autowired
	HobbyRepository hRepo;
	@Autowired
	FavoriteRepository fRepo;
	@Autowired
	IdealRepository iRepo;
	@Autowired
	MatchingGradeRepository gradeRepo;
	@Autowired
	NotToMeetRepository notRepo;

	@GetMapping(value = "/matching")
	@ResponseBody
	public String matchingRequest(String target, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		System.out.println(user);

		MatchingVO mats = mRepo.matcheck(user.getId(), target);

		if (mats == null) {
			MatchingVO vo = MatchingVO.builder().id(user).target(uRepo.findById(target).get()).mconfirm(0).build();
			System.out.println(vo);
			MatchingVO saveResult = mRepo.save(vo);
		} else {
			mRepo.findById(mats.getMId()).ifPresent((mat) -> {
				mat.setMconfirm(1);
				mRepo.save(mat);
			});
		}
		
		user.setCoin(user.getCoin() - 3);
		uRepo.save(user);

		return "매칭 신청이 되었습니다." + "상대방의 응답을 기다려주세요.";
	}

	/*
	 * @GetMapping(value = "/matchingResponse")
	 * 
	 * @ResponseBody public void matchingResponse2(String id, HttpSession session) {
	 * UserVO user = (UserVO) session.getAttribute("user");
	 * System.out.println(user); System.out.println(id); MatchingVO vo =
	 * MatchingVO.builder() .id(user.getId()) .vo.setM_confirm(0) .build();
	 * System.out.println(vo); MatchingVO saveResult = mRepo.save(vo); return
	 * saveResult.getM_id() + "OK";
	 * 
	 * return "매칭 신청이 되었습니다." + "상대방의 응답을 기다려주세요.";
	 * 
	 * }
	 * 
	 * @GetMapping(value = "/matching/test") public String aa() {
	 * 
	 * return "/matching/NewFile"; }
	 */

	@GetMapping(value = "/matView")
	@ResponseBody
	public ModelAndView matchingResponse(HttpSession session, ModelAndView mv ,String target) {
		UserVO user = (UserVO) session.getAttribute("user");
		List<MatchingVO> pickMeList = mRepo.findByTargetAndMconfirm(user, 0); 
		List<MatchingVO> pickYou = mRepo.findById(user);
		
		mv.addObject("pickYou", pickYou);
		mv.addObject("pickMeList", pickMeList); 
		
		
		mv.setViewName("/matching/matView");
		
		System.out.println(pickMeList.size() + "왔냐고오오오오");
		System.out.println(pickYou + "왔");
		

		return mv;

	}

	/*
	 * @GetMapping(value = "/matView")
	 * 
	 * @ResponseBody public MatchingVO matcheck(HttpSession session ,String target)
	 * { UserVO user = (UserVO) session.getAttribute("user"); MatchingVO list =
	 * mRepo.matcheck(user.getId(),target); System.out.println(list.toString() +
	 * "왔냐고오오오오"); //System.out.println(pickMeList.get(0) + "오라고오오");
	 * 
	 * //mv.addObject("pickMeList", pickMeList);
	 * //mv.setViewName("/matching/matView");
	 * 
	 * return list; }
	 */

	@Transactional
	@GetMapping(value = "/matYes")
	public String matchingYes(String pickid, HttpSession session) {

		pickid = pickid.replaceAll("\"", "");

		UserVO user = (UserVO) session.getAttribute("user");
		mRepo.modifyMatching(pickid, user.getId());
		System.out.println(pickid + "--id:" + user.getId());
		return "redirect:/myPage/"+user.getId();
	}

	
	@Transactional
	@GetMapping(value = "/matNo")
	public String matchingNo(String pickid, HttpSession session) {
		System.out.println(pickid);
		pickid = pickid.replaceAll("\"", "");
		UserVO user = (UserVO) session.getAttribute("user");
		mRepo.deletMatching(pickid, user.getId());

		return "redirect:/myPage/"+user.getId();
	}
	
	/*
	 * @GetMapping(value = "/responseMat")
	 * 
	 * @ResponseBody public ModelAndView responseMat(String pickid , HttpSession
	 * session ,ModelAndView mv) { System.out.println("오라오라"+pickid); pickid =
	 * pickid.replaceAll("\"", ""); UserVO user = (UserVO)
	 * session.getAttribute("user");
	 * 
	 * 
	 * System.out.println("와라라" +pickYou.size());
	 * 
	 * mv.setViewName("/matching/matView");
	 * 
	 * return mv; }
	 */
	/*
	 * @GetMapping(value = "/matching/response") public String aa() {
	 * 
	 * return "/matching/NewFile"; }
	 */

	@GetMapping("/matchingList")
	public String matchingList(Model model,HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		List<FavoriteVO> favoriteVOs = fRepo.findByuserId(user.getId());
		List<HobbyVO> hobbyVOs = hRepo.findByuserId(user.getId());
		List<IdealTypeVO> idealTypeVOs = iRepo.findByuserId(user.getId());
		
		Gender yourGender = null;
		if(user.getGender()==Gender.MALE) {
			yourGender = Gender.FEMALE;
		}else {
			yourGender = Gender.MALE;
		}
		
		//매칭 점수 초기화
		if(gradeRepo.existsByUser(user)) {
			List<matchingGrade> mgrade = gradeRepo.findByUser(user);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			long diff = now.getTime()-mgrade.get(0).getMakeTime().getTime();
			diff = (diff/1000)/60/60;
			log.info(diff+"="+now.getTime()+"-"+mgrade.get(0).getMakeTime().getTime());
			if(diff<24) {
				List<matchingGrade> grades = gradeRepo.findByUserOrderByGradeDesc(user);
				Map<matchingGrade,List<String>> targets = new HashMap<>();
				for(int i =0; i<grades.size(); i++) {
					UserVO target = grades.get(i).getTarget();
					List<String> favList = new ArrayList<>();
					mfRepo.findByUser(target).forEach(mf->{
						favList.add(mf.getFavorite().getFavoriteValue());
					});
					mhRepo.findByUser(target).forEach(mh->{
						favList.add(mh.getHobby().getHobbyValue());
					});
					miRepo.findByUser(target).forEach(mi->{
						favList.add(mi.getIdeal().getIdealValue());
					});
					targets.put(grades.get(i), favList);
				}
				model.addAttribute("targets",targets);
				return "/fragment/userslider";
			}
		}
		
		gradeRepo.deleteByUser(user);
		List<UserVO> allUserVOs = uRepo.findByGender(yourGender);
		//이미 매칭된 리스트
		//아는 사람 만나지 않기
		List<UserVO> alreadyList = new ArrayList<>();
		List<UserVO> uyeonList = new ArrayList<>();
		mRepo.findByTargetAndMconfirm(user, 1).forEach(m->{
			alreadyList.add(m.getId());
		});
		notRepo.findByUser(user).forEach(n->{
			UserVO nuser = uRepo.findByPhone(n.getPhone());
			alreadyList.add(nuser);
		});
		//우연히 만나기로 보낸 사람
		mRepo.findByTargetAndMconfirm(user, 0).forEach(m->{
			if(m.getId().getMachingConfirm().equals("uyeon")) {
				uyeonList.add(m.getId());
			}
		});
		
		aa:for(UserVO you: allUserVOs) {
			if(alreadyList.contains(you)) {
				continue aa;
			}else if(uyeonList.contains(you)) {
				matchingGrade gradeVo = matchingGrade.builder()
						.user(user)
						.target(you)
						.grade(1000)
						.build();
				gradeRepo.save(gradeVo);
				
			}else {
				int grade = 0;
				List<MFavoriteVO> mFavoriteVOs = mfRepo.findByUser(you);
				List<MHobbyVO>mHobbyVOs = mhRepo.findByUser(you);
				List<MIdealVO>mIdealVOs = miRepo.findByUser(you);
				for(MFavoriteVO m: mFavoriteVOs) {
					for(FavoriteVO f:favoriteVOs) {
						if(m.getFavorite().getFavoriteId()==f.getFavorite().getFavoriteId()) {
							grade++;
						}
					}
				}
				for(MHobbyVO m: mHobbyVOs) {
					for(HobbyVO f:hobbyVOs) {
						if(m.getHobby().getHobbyId()==f.getHobby().getHobbyId()) {
							grade++;
						}
					}
				}
				for(MIdealVO m: mIdealVOs) {
					for(IdealTypeVO f:idealTypeVOs) {
						if(m.getIdeal().getIdealId()==f.getIdeal().getIdealId()) {
							grade++;
						}
					}
				}
				matchingGrade gradeVo = matchingGrade.builder()
						.user(user)
						.target(you)
						.grade(grade)
						.build();
				gradeRepo.save(gradeVo);
			}
			
		}
		
		List<matchingGrade> grades = gradeRepo.findByUserOrderByGradeDesc(user);
		Map<matchingGrade,List<String>> targets = new HashMap<>();
		for(int i =0; i<grades.size(); i++) {
			if(i<10) {
				UserVO target = grades.get(i).getTarget();
				List<String> favList = new ArrayList<>();
				mfRepo.findByUser(target).forEach(mf->{
					favList.add(mf.getFavorite().getFavoriteValue());
				});
				mhRepo.findByUser(target).forEach(mh->{
					favList.add(mh.getHobby().getHobbyValue());
				});
				miRepo.findByUser(target).forEach(mi->{
					favList.add(mi.getIdeal().getIdealValue());
				});
				targets.put(grades.get(i), favList);
			}
			
		}
		model.addAttribute("targets",targets);
		System.out.println("안녕!! "+targets);
		
		return "/fragment/userslider";
		
		
	}
	
	@PostMapping(value = "/matchingBlock")
	@ResponseBody
	public void matchingBlock(String buser,String mid) {
		UserVO user = uRepo.findById(mid).get();
		UserVO block = uRepo.findById(buser).get();
		NotToMeetVO not = NotToMeetVO.builder()
				.name(block.getName())
				.phone(block.getPhone())
				.user(user)
				.build();
		notRepo.save(not);
		matchingGrade grade = gradeRepo.findByUserAndTarget(user, block);
		grade.setBlock(1);
		gradeRepo.save(grade);
	}
}
