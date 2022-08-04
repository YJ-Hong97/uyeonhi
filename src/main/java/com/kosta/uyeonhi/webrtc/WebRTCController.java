package com.kosta.uyeonhi.webrtc;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.JsonObject;
import com.kosta.uyeonhi.mypage.ChatUserState;
import com.kosta.uyeonhi.mypage.ChattingRoomRepository;
import com.kosta.uyeonhi.mypage.ChattingRoomVO;
import com.kosta.uyeonhi.mypage.ChattingUsersRepository;
import com.kosta.uyeonhi.mypage.ChattingUsersVO;
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
	@MessageMapping("/video/joined-room-info/{roomNo}")
	public void joinRoom(@Header("simpSessionId")String sessionId,JSONObject ob){
		UserVO user = uRepo.findById(ob.get("from").toString()).get();
		Long roomNo = Long.parseLong(ob.get("at").toString());
		ChattingRoomVO room = chatRoomRepo.findById(roomNo).get();
		
		if(chatMap.keySet().contains(room+"")) {
			chatMap.get(room).add(user.getNickname());
		}else {
			List<String> users = new ArrayList<>();
			users.add(user.getNickname());
			chatMap.put(room.getRoomNo()+"",users);
		}
		
		String destination = "/sub/video/joined-room-info/"+roomNo;
		log.info(destination);
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
	public void answerCall(JSONObject ob,@PathVariable("roomNo")String roomNo) {
		template.convertAndSend("/sub/video/caller-info/"+roomNo, ob);
	}
	
}
