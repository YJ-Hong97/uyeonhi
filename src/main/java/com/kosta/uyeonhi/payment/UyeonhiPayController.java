package com.kosta.uyeonhi.payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/uyeonhi/*")
public class UyeonhiPayController {
	@Autowired
	PayRepository payRepo;
	
	@RequestMapping("/uyeonPay")
	public ModelAndView uyeonPay(ModelAndView mv) {
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
	public String payInsert(@RequestBody Map<String, Object> map) throws ParseException {
		Map<String, Object> user = (Map<String, Object>) map.get("params");
		String userId= (String) map.get("userId");//사용자 id
		int amount=(int) map.get("price");//구매금액
		int uNum = amount/100;//우연 갯수
		
		String date = (String) map.get("requested_at");//구매일자
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date rdate = formatter.parse(date);
		String rdate2 = formatter.format(rdate);
		java.sql.Date regdate = java.sql.Date.valueOf(rdate2);
		
		System.out.println(user);
		System.out.println(map);
		
		PayVO vo = new PayVO();
		vo.setId(userId);
		vo.setUNum(uNum);
		vo.setRegdate(regdate);
		vo.setAmount(amount);
		
		payRepo.save(vo);
		return "succes";
	}
	
}
