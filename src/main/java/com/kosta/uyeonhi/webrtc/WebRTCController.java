package com.kosta.uyeonhi.webrtc;

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
	
	Map<ChattingRoomVO,List<ChattingUsersVO>> roomMap = new HashMap<>();
	ArrayList<String> dataArrayList = new ArrayList<>();
	@RequestMapping("/video/socket/{roomNo}")
	public ModelAndView test(ModelAndView mnv, HttpSession session,@PathVariable("roomNo")long roomNo) {
		ChattingRoomVO room = chatRoomRepo.findById(roomNo).get();
		List<ChattingUsersVO> users = chatUserRepo.findByRoom(room);
		UserVO user = (UserVO) session.getAttribute("user");
		for(ChattingUsersVO cuser:users) {
			if(cuser.getUser().equals(user)) {
				cuser.setState(ChatUserState.connect);
			}
		}
		roomMap.put(room, users);
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
		List<String> nickList = new ArrayList<>();
		List<ChattingUsersVO> users = chatUserRepo.findByRoom(room);
		for(ChattingUsersVO cuser: users) {
			if(cuser.getUser().equals(user)) {
				cuser.setState(ChatUserState.connect);
				nickList.add(user.getNickname());
			}
		}
		String destination = "/sub/video/joined-room-info/"+roomNo;
		log.info(destination);
		template.convertAndSend(destination,nickList);
		
	}
	@MessageMapping("/video/sendSignal")
	@SendTo("/sub/video/joined-room-info")
	public JSONObject sendSignal(JSONObject ob){
		System.out.println(ob);
		return ob;
		
	}
	@MessageMapping("/video/caller-info/{roomNo}")
	public void caller(@PathVariable("roomNo")Long roomNo,JSONObject ob) {
		
		template.convertAndSend("/sub/video/caller-info/"+roomNo, ob);
	}
	@MessageMapping("/video/callee-info/{roomNo}")
	public void answerCall(JSONObject ob) {
		String roomNo = ob.get("at").toString();
		JSONObject signal = (JSONObject) ob.get("signal");
		template.convertAndSend("/sub/video/caller-info/"+roomNo, signal);
	}
	
}
