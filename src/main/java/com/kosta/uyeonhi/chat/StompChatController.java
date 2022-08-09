package com.kosta.uyeonhi.chat;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Controller
@RequiredArgsConstructor
@Log4j2
public class StompChatController {
	@Autowired
	SimpMessagingTemplate template;
	
	@Autowired
	ChatMessageRepository mrepo;

	@Autowired
	ChatRoomRepository rrepo;
	
	//Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message){
    	log.info(message);
        message.setMessage(message.getWriter().getNickname() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
    
    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message){
    	log.info(message);
    	mrepo.save(message);
//    	
//    	String api_key = "NCSNK3ZLZJS8QN23";
//        String api_secret = "QPO5Z4CQRJVFGWRIEZRHBOJ4HXYK91LX";
//        Message coolsms = new Message(api_key, api_secret);
//
//        // 4 params(to, from, type, text) are mandatory. must be filled
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("to", "01047486110");	// 수신전화번호
//        params.put("from", "01047486110");	// 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
//        params.put("type", "SMS");
//        params.put("text", "첫번째 보내는 테스트 문자 메시지!");
//        params.put("app_version", "test app 1.2"); // application name and version
//
//        try {
//          JSONObject obj = (JSONObject) coolsms.send(params);
//          System.out.println(obj.toString());
//        } catch (CoolsmsException e) {
//          System.out.println(e.getMessage());
//          System.out.println(e.getCode());
//        }
    	
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}










