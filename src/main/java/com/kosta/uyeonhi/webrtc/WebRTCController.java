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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.JsonObject;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.extern.java.Log;
@Log
@Controller
public class WebRTCController {
	
	
	@Autowired
	UserRepository uRepo;
	
	ArrayList<String> dataArrayList = new ArrayList<>();
	@RequestMapping("/video/socket")
	public ModelAndView test(ModelAndView mnv, HttpSession session) {
		UserVO user = (UserVO)session.getAttribute("user");
		mnv.addObject("user",user);
		mnv.setViewName("webrtc/index");
		return mnv;
	}
	@MessageMapping("/video/joined-room-info")
	@SendTo("/sub/video/joined-room-info")
	public ArrayList<String> joinRoom(@Header("simpSessionId")String sessionId,JSONObject ob){
		UserVO user = uRepo.findById(ob.get("from").toString()).get();
		dataArrayList.add(user.getId());
		return dataArrayList;
		
	}
	@MessageMapping("/video/sendSignal")
	@SendTo("/sub/video/joined-room-info")
	public JSONObject sendSignal(JSONObject ob){
		System.out.println(ob);
		return ob;
		
	}
	@MessageMapping("/video/caller-info")
	@SendTo("/sub/video/caller-info")
	public JSONObject caller(JSONObject ob) {
		log.info("송신자Call");
		return ob;
	}
	@MessageMapping("/video/callee-info")
	@SendTo("/sub/video/callee-info")
	public JSONObject answerCall(JSONObject ob) {
		log.info("수신자 응답");
		return ob;
	}
	
}
