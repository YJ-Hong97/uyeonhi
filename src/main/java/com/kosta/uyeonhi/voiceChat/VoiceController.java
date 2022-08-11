package com.kosta.uyeonhi.voiceChat;


import static org.hamcrest.CoreMatchers.nullValue;


import java.io.Console;
import java.sql.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.uyeonhi.mypage.ChatType;
import com.kosta.uyeonhi.mypage.ChattingRoomRepository;
import com.kosta.uyeonhi.mypage.ChattingRoomVO;
import com.kosta.uyeonhi.mypage.ChattingUsersRepository;
import com.kosta.uyeonhi.mypage.ChattingUsersVO;
import com.kosta.uyeonhi.promise.PromiseRepository;
import com.kosta.uyeonhi.promise.PromiseVO;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.webrtc.SignalEntity;
import com.kosta.uyeonhi.webrtc.VideoChatRepository;
import com.kosta.uyeonhi.webrtc.VideoChatVO;
import com.kosta.uyeonhi.webrtc.VideoMessageRepository;

import lombok.extern.java.Log;
@Log
@RestController
public class VoiceController {
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
	@Autowired
	PromiseRepository promiseRepository;
	Map<String, List<String>> chatMap = new HashMap<>();
	@RequestMapping("/audio/socket/{roomNo}")
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
		mnv.setViewName("voice/index");
		return mnv;
	}
	@RequestMapping("/audio/existRoom/{mid}/{uid}")
	public String existRoom(ModelAndView mnv, HttpSession session,@PathVariable("mid")String mid,@PathVariable("uid")String uid) {
		List<ChattingUsersVO> muser = chatUserRepo.findByUser(uRepo.findById(mid).get());
		List<ChattingUsersVO> uuser = chatUserRepo.findByUser(uRepo.findById(uid).get());
		Long roomNo = null;
		aa:for(ChattingUsersVO m: muser) {
			for(ChattingUsersVO u: uuser) {
				if(m.getRoom().getRoomNo()==u.getRoom().getRoomNo()) {
					roomNo = m.getRoom().getRoomNo();
					ChattingRoomVO room = chatRoomRepo.findById(roomNo).get();
					if(room.getType()==ChatType.voice) {
						break aa;
					}else {
						roomNo = null;
					}
				}
			}
		}
		if(roomNo != null) {
			return roomNo.toString();
		}else {
			return "false";
		}
		
		
	}
	@PostMapping("/audio/makeRoom")
	public ModelAndView makeRoom(HttpSession session,String title,String uid, ModelAndView mnv) {
		log.info(uid);
		UserVO user = (UserVO) session.getAttribute("user");
		ChattingRoomVO room = ChattingRoomVO.builder()
				.user(user)
				.title(title)
				.type(ChatType.voice)
				.build();
		chatRoomRepo.save(room);
		UserVO uuser = uRepo.findById(uid).get();
		ChattingUsersVO mChattingUsersVO = ChattingUsersVO.builder()
				.room(room)
				.user(user)
				.build();
		chatUserRepo.save(mChattingUsersVO);
		ChattingUsersVO uChattingUsersVO = ChattingUsersVO.builder()
				.room(room)
				.user(uuser)
				.build();
		chatUserRepo.save(uChattingUsersVO);
		mnv.setViewName("redirect:/audio/socket/"+room.getRoomNo());
		return mnv;
	}
	@RequestMapping("/audio/deleteRoom/{roomNo}")
	public void deleteRoom(@PathVariable("roomNo")Long roomNo) {
		chatUserRepo.deleteByRoom(chatRoomRepo.findById(roomNo).get());
		chatRoomRepo.deleteByRoomNo(roomNo);
		
	}
	@MessageMapping("/audio/joined-info/{roomNo}")
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
		String destination = "/sub/audio/joined-info/"+roomNo;
		template.convertAndSend(destination,chatMap);
		
	}
	@MessageMapping("/audio/caller-info/{roomNo}")
	public void caller(SignalEntity ob) {
		log.info("caller 송신");
		log.info(ob.toString());
		String roomNo = ob.getSignalId();
		String destination = "/sub/audio/caller-info/"+roomNo;
	
		template.convertAndSend(destination,ob);
	}
	@MessageMapping("/audio/callee-info/{roomNo}")
	public void answerCall(SignalEntity ob) {
		String roomNo = ob.getSignalId();
		template.convertAndSend("/sub/audio/callee-info/"+roomNo, ob);
	}
	@MessageMapping("/audio/caller-pause/{roomNo}")
	public void callerPause(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		template.convertAndSend("/sub/audio/caller-pause/"+roomNo,ob);
	}
	@MessageMapping("/audio/callee-pause/{roomNo}")
	public void calleePause(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		template.convertAndSend("/sub/audio/callee-pause/"+roomNo,ob);
	}
	@MessageMapping("/audio/caller-play/{roomNo}")
	public void callerPlay(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		template.convertAndSend("/sub/audio/caller-play/"+roomNo,ob);
	}
	@MessageMapping("/audio/callee-play/{roomNo}")
	public void calleePlay(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		template.convertAndSend("/sub/audio/callee-play/"+roomNo,ob);
	}
	@MessageMapping("/audio/message/{roomNo}")
	public void chat(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		String nickname = ob.get("nickname").toString();
		String message = ob.get("message").toString();
		
		template.convertAndSend("/sub/audio/message/"+roomNo, ob);
	}
	@RequestMapping("/audio/initiator-disconnect/{mid}/{roomNo}")
	public void disconnect(@PathVariable("mid")String mid,@PathVariable("roomNo")String roomNo) {
		
		if(chatMap.containsKey(roomNo)) {
			chatMap.get(roomNo).remove(mid);
		}else {
			
		}
		log.info(chatMap.toString());
		template.convertAndSend("/sub/audio/initiator-out/"+roomNo, "");
		
	}
	@RequestMapping("/audio/second-disconnect/{mid}/{roomNo}")
	public void disconnectSecond(@PathVariable("mid")String mid,@PathVariable("roomNo")String roomNo) {
		if(chatMap.containsKey(roomNo)) {
			chatMap.get(roomNo).remove(mid);
		}else {
			
		}
		log.info(chatMap.toString());
		template.convertAndSend("/sub/audio/second-out/"+roomNo, "");
	}
	@MessageMapping("/audio/promise-offer/{roomNo}")
	public void promise(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		Date date = Date.valueOf(ob.get("date").toString());
		UserVO user = uRepo.findById(ob.get("offer").toString()).get();
		PromiseVO promise = PromiseVO.builder()
				.me(user)
				.time(date)
				.location(ob.get("location").toString())
				.title(ob.get("title").toString())
				.build();
		promiseRepository.save(promise);
		ob.put("pid", promise.getProId());
		template.convertAndSend("/sub/audio/promise-accept/"+roomNo, ob);
	}
	@MessageMapping("/audio/promise-accept/{roomNo}")
	public void promiseSuccess(JSONObject ob) {
		String roomNo = ob.get("roomNo").toString();
		UserVO user = uRepo.findById(ob.get("answer").toString()).get();
		PromiseVO promise = promiseRepository.findById((long)Integer.parseInt(ob.get("pid").toString())).get();
		promise.setPromise_ox("o");
		promise.setYou(user);
		promiseRepository.save(promise);
		template.convertAndSend("/sub/audio/promise-success/"+roomNo, ob);
		
	}
}
