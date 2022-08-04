package com.kosta.uyeonhi.reply;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDTO {

	private Long reply_id;

	private String reply_content;

	private UserVO user;

	private Long boardId;

	private String date;

	private String depth;

	private int reply_like;

	private boolean isRemoved = false;

	private Long parentId;

	public ReplyResponseDTO(Reply reply) {
		this.reply_id = reply.getReply_id();
		this.reply_content = reply.getReply_content();
		this.user = reply.getUser();
		this.boardId = reply.getBoard().getBoardId();
		if(reply.getUpdateDate() == null) {
			this.date = Time.txtDate(reply.getRegdate());
		} else {
			this.date = Time.txtDate(reply.getUpdateDate());
		}
		this.depth = reply.getDepth();
		this.reply_like = reply.getReply_like();
		this.isRemoved = reply.isRemoved();
		if(reply.getParent()== null) {
			parentId = null;
		} else {
			this.parentId = reply.getParent().getReply_id();
		}
	}
	
}
