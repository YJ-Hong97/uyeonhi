package com.kosta.uyeonhi.push;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.uyeonhi.reply.ReplyService;
import com.kosta.uyeonhi.sns.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
	
	private final NotificationServiceImpl notificationService;
	
	@GetMapping("/list")
	@ResponseBody
	public List<Notification> notiList( String userId){
		System.out.println("여기까지는 오나?");
		List<Notification> list = notificationService.getNotiList(userId);
		return list;
	}
}
