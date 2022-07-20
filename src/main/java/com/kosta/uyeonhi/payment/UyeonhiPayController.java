package com.kosta.uyeonhi.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
