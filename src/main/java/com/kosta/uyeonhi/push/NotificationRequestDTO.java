package com.kosta.uyeonhi.push;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kosta.uyeonhi.reply.Reply;
import com.kosta.uyeonhi.reply.ReplyRequestDTO;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class NotificationRequestDTO {
	
	private String senderId;
	private String receiverId;
	private String notificationType;
	private Boolean isRead;
	private int boardId;
	private int replyId;
	private LocalDateTime regdate;
	
	public Notification toEntity() {
		return Notification.builder()
				.senderId(senderId)
				.notificationType(notificationType)
				.isRead(isRead)
				.boardId(boardId)
				.replyId(replyId)
				.regdate(regdate)
				.build();
	}
	
}

