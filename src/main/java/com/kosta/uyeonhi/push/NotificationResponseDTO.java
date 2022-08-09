package com.kosta.uyeonhi.push;

import java.time.LocalDateTime;

import com.kosta.uyeonhi.reply.ReplyResponseDTO;
import com.kosta.uyeonhi.reply.Time;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {
	private String senderId;
	private UserVO receiverId;
	private String notificationType;
	private Boolean isRead;
	private int boardId;
	private int replyId;
	private String regdate;
	
	
	public NotificationResponseDTO(Notification notification) {
		this.senderId = notification.getSenderId();
		this.receiverId = notification.getReceiverId();
		this.notificationType = notification.getNotificationType();
		this.isRead = notification.getIsRead();
		this.regdate = Time.convertLocaldatetimeToTime(notification.getRegdate());
		this.boardId = notification.getBoardId();
		this.replyId = notification.getReplyId();
	}
}
