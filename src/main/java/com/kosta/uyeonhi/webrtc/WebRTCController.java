package com.kosta.uyeonhi.webrtc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.uyeonhi.mypage.ChattingRoomRepository;
import com.kosta.uyeonhi.mypage.ChattingRoomVO;
import com.kosta.uyeonhi.mypage.ChattingUsersRepository;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.extern.java.Log;
@Log
@RestController
public class WebRTCController {
	@Autowired
	ChattingRoomRepository chatRoomRepo;
	@Autowired
	ChattingUsersRepository chatUserRepo;
	@Autowired
	SimpMessagingTemplate template;
	@Autowired
	UserRepository uRepo;
	Map<String, List<String>> chatMap = new HashMap<>();
	@RequestMapping("/video/socket/{roomNo}")
	public ModelAndView test(ModelAndView mnv, HttpSession session,@PathVariable("roomNo")long roomNo) {
		ChattingRoomVO room = chatRoomRepo.findById(roomNo).get();
		UserVO user = (UserVO) session.getAttribute("user");
		
		mnv.addObject("room",room);
		mnv.addObject("user",user);
		mnv.setViewName("webrtc/index");
		return mnv;
	}
	@MessageMapping("/video/joined-info/{roomNo}")
	public void joinRoom(@Header("simpSessionId")String sessionId,JSONObject ob){
		String roomNo = ob.get("roomNo").toString();
		String mid = ob.get("mid").toString();
		
		if(chatMap.keySet().contains(roomNo)) {
			if(chatMap.get(roomNo).size()==2) {
				chatMap.get(roomNo).clear();
			}
			chatMap.get(roomNo).add(mid);
		}else {
			List<String> members = new ArrayList<>();
			members.add(mid);
			chatMap.put(roomNo, members);
		}
		String destination = "/sub/video/joined-info/"+roomNo;
		template.convertAndSend(destination,chatMap);
		
	}
	@MessageMapping("/video/caller-info/{roomNo}")
	public void caller(SignalEntity ob) {
		log.info("caller 송신");
		log.info(ob.toString());
		String roomNo = ob.getSignalId();
		String destination = "/sub/video/caller-info/"+roomNo;
	
		template.convertAndSend(destination,ob);
	}
	@MessageMapping("/video/callee-info/{roomNo}")
	public void answerCall(SignalEntity ob) {
		String roomNo = ob.getSignalId();
		template.convertAndSend("/sub/video/callee-info/"+roomNo, ob);
	}
	
}
