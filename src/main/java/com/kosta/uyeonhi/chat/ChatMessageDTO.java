package com.kosta.uyeonhi.chat;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity @Table(name = "tbl_message")
public class ChatMessageDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long chatid;
	
	@ManyToOne
	private UserVO writer;
	
	@Column(length = 1000)
	private String message;
	
	private String regdate;
	
	private String roomId;
}













