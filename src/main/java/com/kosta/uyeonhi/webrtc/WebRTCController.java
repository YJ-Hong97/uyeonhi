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
	@Autowired
	VideoChatRepository videoRepo;
	@Autowired
	VideoMessageRepository messageRepo;
	Map<String, List<String>> chatMap = new HashMap<>();
	@RequestMapping("/video/socket/{roomNo}")
	public ModelAndView test(ModelAndView mnv, HttpSession session,@PathVariable("roomNo")long roomNo) {
		ChattingRoomVO room = chatRoomRepo.findById(roomNo).get();
		List<ChattingUsersVO> chatusers = chatUserRepo.findByRoom(room);
		UserVO user = (UserVO) session.getAttribute("user");
		if(videoRepo.existsByRoom(room)) {
	
		}else {
			VideoChatVO video = VideoChatVO.builder()
					.room(room)
					.build();
			videoRepo.save(video);
		}
		mnv.addObject("room",room);
		mnv.addObject("user",user);
		mnv.addObject("chatusers", chatusers);
		mnv.setViewName("webrtc/index");
		return mnv;
	}
	@MessageMapping("/video/joined-info/{roomNo}")
	public void joinRoom(@Header("simpSessionId")String sessionId,JSONObject ob){
		String roomNo = ob.get("roomNo").toString();
		String mid = ob.get("mid").toString();
		
		if(chatMap.keySet().contains(roomNo)) {
			
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
	@MessageMapping("/video/caller-pause/{roomNo}")
	public void callerPause(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		template.convertAndSend("/sub/video/caller-pause/"+roomNo,ob);
	}
	@MessageMapping("/video/callee-pause/{roomNo}")
	public void calleePause(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		template.convertAndSend("/sub/video/callee-pause/"+roomNo,ob);
	}
	@MessageMapping("/video/caller-play/{roomNo}")
	public void callerPlay(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		template.convertAndSend("/sub/video/caller-play/"+roomNo,ob);
	}
	@MessageMapping("/video/callee-play/{roomNo}")
	public void calleePlay(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		template.convertAndSend("/sub/video/callee-play/"+roomNo,ob);
	}
	@MessageMapping("/video/message/{roomNo}")
	public void chat(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		String nickname = ob.get("nickname").toString();
		ChattingRoomVO room = chatRoomRepo.findById(Long.parseLong(roomNo)).get();
		VideoChatVO video = videoRepo.findByRoom(room);
		VideoChatMessageVO message = VideoChatMessageVO.builder()
				.videoChatVO(video)
				.message(ob.get("message").toString())
				.nickname(nickname)
				.build();
		messageRepo.save(message);
		List<VideoChatMessageVO> messagelist = messageRepo.findByVideoChatVO(video);
		List<Map<String, String>> messageMaps = new ArrayList<>();
		for(VideoChatMessageVO m:messagelist) {
			Map<String,String> map = new HashMap<>();
			map.put(m.getNickname(), m.getMessage());
			messageMaps.add(map);
		}
		template.convertAndSend("/sub/video/message/"+roomNo, messageMaps);
	}
	@RequestMapping("/video/initiator-disconnect/{mid}/{roomNo}")
	public void disconnect(@PathVariable("mid")String mid,@PathVariable("roomNo")String roomNo) {
		
		if(chatMap.containsKey(roomNo)) {
			chatMap.get(roomNo).remove(mid);
		}else {
			
		}
		log.info(chatMap.toString());
		template.convertAndSend("/sub/video/initiator-out/"+roomNo, "");
		
	}
	@RequestMapping("/video/second-disconnect/{mid}/{roomNo}")
	public void disconnectSecond(@PathVariable("mid")String mid,@PathVariable("roomNo")String roomNo) {
		if(chatMap.containsKey(roomNo)) {
			chatMap.get(roomNo).remove(mid);
		}else {
			
		}
		log.info(chatMap.toString());
		template.convertAndSend("/sub/video/second-out/"+roomNo, "");
	}
}
