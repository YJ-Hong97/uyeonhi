package com.kosta.uyeonhi.push;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.uyeonhi.reply.ReplyService;
import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
	
	private final NotificationServiceImpl notificationService;
	
	@GetMapping("/list")
	@ResponseBody
	public List<Notification> notiList(String userId, Model model){
		List<Notification> list = notificationService.getNotiList(userId);
		model.addAttribute("count", notificationService.notificationCount(userId));
		return list;
	}
	
	@GetMapping("/count")
	@ResponseBody
	public int notiCount(HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		int alarm = notificationService.notificationCount(user.getId());
		
		session.setAttribute("alCount", alarm);
		return alarm;
	}
}
