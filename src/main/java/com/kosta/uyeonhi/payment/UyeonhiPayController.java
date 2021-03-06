package com.kosta.uyeonhi.payment;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/uyeonhi/*")
public class UyeonhiPayController {
	@Autowired
	PayRepository payRepo;

	@Autowired
	UserRepository userRepo;

	@RequestMapping("/uyeonPay")
	public ModelAndView uyeonPay(ModelAndView mv) {
		String id = "hyj1077";

		UserVO user = userRepo.findById(id).get();
		mv.addObject("coin", user.getCoin());
		mv.setViewName("payment/pay");
		return mv;
	}

	@RequestMapping("/payView1.go")
	public ModelAndView view1(ModelAndView mv) {
		mv.setViewName("payment/view1");
		return mv;
	}

	@RequestMapping("/payView2.go")
	public ModelAndView view2(ModelAndView mv) {
		String id = "hyj1077";

		UserVO user = userRepo.findById(id).get();
		List<PayVO> list = payRepo.findByUser(user);
		mv.addObject("paylist", list);

		if (list.isEmpty())
			mv.addObject("msg", "o");
		else
			mv.addObject("msg", "x");

		mv.setViewName("payment/view2");
		return mv;
	}

	@RequestMapping("/payView3.go")
	public ModelAndView view3(ModelAndView mv) {
		mv.setViewName("payment/view3");
		return mv;
	}

	@PostMapping("/payInsert.go")
	@ResponseBody
	public String payInsert(@RequestBody Map<String, Object> map, HttpServletRequest request)
			throws ParseException, UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		// int userId =Integer.parseInt(request.getParameter("memberid")) ;

		Map<String, Object> user = (Map<String, Object>) map.get("params");
		String userId = (String) user.get("userId");// ????????? id
		int amount = (int) map.get("price");// ????????????
		int unum = amount / 100;// ?????? ??????

		System.out.println(map);
		System.out.println(user);
		String date = (String) map.get("requested_at");// ????????????
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date rdate = formatter.parse(date);
		String rdate2 = formatter.format(rdate);
		java.sql.Date regdate = java.sql.Date.valueOf(rdate2);

		UserVO userVO = userRepo.findById(userId).get();

		int cur_unum = unum + userVO.getCoin();// ?????? ?????? ??????
		userVO.setCoin(cur_unum);

		PayVO vo = new PayVO();
		vo.setUser(userVO);
		vo.setUnum(unum);
		vo.setCurrent_unum(cur_unum);
		vo.setRegdate(regdate);
		vo.setAmount(amount);

		PayVO insertPay = null;
		insertPay = payRepo.save(vo);
		
		if(insertPay == null) 
			return "fail";
		
		userRepo.save(userVO);
		return "success";
	}
}
