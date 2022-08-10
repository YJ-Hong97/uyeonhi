package com.kosta.uyeonhi.matching;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

@Controller
public class MatchingController {

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
	public ModelAndView matchingResponse(HttpSession session, ModelAndView mv) {
		UserVO user = (UserVO) session.getAttribute("user");

		List<MatchingVO> pickMeList = mRepo.findByTargetAndMconfirm(user, 0); //
		mv.addObject("pickMeList", pickMeList); //
		mv.setViewName("/matching/matView");

		System.out.println(pickMeList.size() + "왔냐고오오오오");
		//System.out.println(pickMeList.get(0) + "오라고오오");

		mv.addObject("pickMeList", pickMeList);
		mv.setViewName("/matching/matView");
			
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
		return "redirect:/myPage/";
	}

	@Transactional
	@GetMapping(value = "/matNo")
	public String matchingNo(String pickid, HttpSession session) {
		System.out.println(pickid);
		pickid = pickid.replaceAll("\"", "");
		UserVO user = (UserVO) session.getAttribute("user");
		mRepo.deletMatching(pickid, user.getId());

		return "redirect:/myPage/";
	}

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
		
		List<UserVO> allUserVOs = uRepo.findByGender(yourGender);
		for(UserVO you: allUserVOs) {
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
		
		List<matchingGrade> grades = gradeRepo.findByUserOrderByGradeDesc(user);
		Map<UserVO,List<String>> targets = new HashMap<>();
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
			targets.put(target, favList);
		}
		model.addAttribute("targets",targets);
		return "/fragment/userslider";
		
		
	}

}
