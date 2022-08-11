package com.kosta.uyeonhi.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.kosta.uyeonhi.signUp.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class NotificationHandler extends TextWebSocketHandler {

	private final NotificationService notificationService;

	private List<WebSocketSession> sessions = new ArrayList<>();
	private Map<String, WebSocketSession> userSessionsMap = new ConcurrentHashMap<String, WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		log.info("Socket 연결");
		sessions.add(session);
		log.info(currentUserName(session));// 현재 접속한 사람
		String senderId = currentUserName(session);
		log.info("보낸사람 아이디" + senderId);
		userSessionsMap.put(senderId, session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("session" + currentUserName(session));
		String msg = message.getPayload(); // 자바스크립트에서 넘어온 메세지
		log.info("msg=" + msg);

		if (StringUtils.isNotEmpty(msg)) {
			log.info("if문 들어옴?");
			String[] strs = msg.split(",");
			if (strs != null && strs.length == 4) {

				String cmd = strs[0];
				String boardWriter = strs[1];
				String replyWriter = strs[2];
				String bno = strs[3];
				log.info("length 성공?" + replyWriter);

				int count = notificationService.notificationCount(boardWriter);
				System.out.println("알림 수는:" + count);
				WebSocketSession replyWriterSession = userSessionsMap.get(replyWriter);
				WebSocketSession boardWriterSession = userSessionsMap.get(boardWriter);

				// 댓글
				if ("reply".equals(cmd) && boardWriterSession != null) {
					log.info("onmessage되나?");
					TextMessage tmpMsg = new TextMessage("<div>"
							+ "<div class='toast-container position-fixed top-0 end-0 p-3' id='toastPlacement'>" + " <div class='toast fade show'>"
							+ " <div class='toast-header'>"
							+ " <svg class='bd-placeholder-img rounded me-2' width='20' height='20' xmlns='http://www.w3.org/2000/svg' aria-hidden='true' preserveAspectRatio='xMidYMid slice' focusable='false'><rect width='100%' height='100%' fill='#007aff'></rect></svg>"
							+ "  <strong class='me-auto'>댓글알림</strong>" + "   <small>11 mins ago</small>" + "  </div>"
							+ " <div class='toast-body'>" + replyWriter + "님이 게시글에 댓글을 작성했습니다." + "  </div>" + " </div>"
							+ "</div>" 
							+ "</div>" 
							);
					boardWriterSession.sendMessage(tmpMsg);
				} else if ("reReply".equals(cmd) && boardWriterSession != null) {
					log.info("onmessage되나?");
					TextMessage tmpMsg = new TextMessage("<div>"
							+ "<div class='toast-container position-fixed top-0 end-0 p-3' id='toastPlacement'>" + " <div class='toast fade show'>"
							+ " <div class='toast-header'>"
							+ " <svg class='bd-placeholder-img rounded me-2' width='20' height='20' xmlns='http://www.w3.org/2000/svg' aria-hidden='true' preserveAspectRatio='xMidYMid slice' focusable='false'><rect width='100%' height='100%' fill='#007aff'></rect></svg>"
							+ "  <strong class='me-auto'>댓글알림</strong>" + "   <small>11 mins ago</small>" + "  </div>"
							+ " <div class='toast-body'>" + replyWriter + "님이 " + boardWriter + "님의 댓글에 대댓글을 작성했습니다."
							+ "  </div>" + " </div>" + "</div>");
					boardWriterSession.sendMessage(tmpMsg);
				}
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {// 연결 해제
		// TODO Auto-generated method stub
		log.info("Socket 끊음");
		sessions.remove(session);
		userSessionsMap.remove(currentUserName(session), session);
	}

	private String currentUserName(WebSocketSession session) {

		String mid = session.getPrincipal().getName();
		return mid;

	}

}
