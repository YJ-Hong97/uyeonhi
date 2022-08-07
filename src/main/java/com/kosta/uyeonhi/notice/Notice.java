package com.kosta.uyeonhi.notice;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.kosta.uyeonhi.signUp.UserVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notice")
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nId;

	@ManyToOne
	private UserVO manager;
	
	
	private String noticeName;
	private String noticeContent;
	
	@CreationTimestamp
	private Date created;
	
	@UpdateTimestamp
	private Date updated;

}
