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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.util.PageMaker;
import com.kosta.uyeonhi.util.PageVO;

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
	public ModelAndView uyeonPay(ModelAndView mv, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		String id = user.getId();
		
		mv.setViewName("payment/pay");
		return mv;
	}

	@RequestMapping("/payView1.go")
	public ModelAndView view1(ModelAndView mv) {
		mv.setViewName("payment/view1");
		return mv;
	}

	@RequestMapping("/payView2.go")
	public ModelAndView view2(ModelAndView mv, PageVO pageVO, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		if (pageVO == null) pageVO = new PageVO();
		
		Pageable page = pageVO.makePageable(0, "regdate");
		Page<PayVO> list = payRepo.findByUser(user,page );

		mv.addObject("result", new PageMaker<>(list));

		if (list.isEmpty())
			mv.addObject("msg", "o");
		else
			mv.addObject("msg", "x");

		mv.setViewName("payment/view2");
		return mv;
	}

	@RequestMapping("/payView22.go")
	@ResponseBody
	public List<PayVO>  view22(ModelAndView mv, PageVO pageVO, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		if (pageVO == null) pageVO = new PageVO();
		
		Pageable page = pageVO.makePageable(0, "regdate");
		Page<PayVO> list = payRepo.findByUser(user,page );
		 
	    return list.getContent();
	}

	@PostMapping("/payInsert.go")
	@ResponseBody
	public String payInsert(@RequestBody Map<String, Object> map, HttpServletRequest request)
			throws ParseException, UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		// int userId =Integer.parseInt(request.getParameter("memberid")) ;

		String userId = user.getId();// 사용자 id
		int amount = (int) map.get("price");// 구매금액
		int unum = amount / 100;// 우연 갯수

		String date = (String) map.get("requested_at");// 구매일자
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date rdate = formatter.parse(date);
		String rdate2 = formatter.format(rdate);
		java.sql.Date regdate = java.sql.Date.valueOf(rdate2);

		UserVO userVO = userRepo.findById(userId).get();

		int cur_unum = unum + userVO.getCoin();// 현재 코인 갯수
		userVO.setCoin(cur_unum);

		PayVO vo = new PayVO();
		vo.setUser(userVO);
		vo.setUnum(unum);
		vo.setCurrent_unum(cur_unum);
		vo.setRegdate(regdate);
		vo.setAmount(amount);

		PayVO insertPay = null;
		insertPay = payRepo.save(vo);

		if (insertPay == null)
			return "fail";

		userRepo.save(userVO);
		return "success";
	}
}
