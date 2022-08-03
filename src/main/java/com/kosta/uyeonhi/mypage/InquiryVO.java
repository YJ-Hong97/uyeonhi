package com.kosta.uyeonhi.mypage;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.kosta.uyeonhi.signUp.UserVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "inquiry")
@Table(name = "inquiry")
public class InquiryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long inquiryId;
	private String title;
	@Column(length = 1000)
	private String content;
	@CreationTimestamp
	@Column(name = "regdate")
	private Timestamp regDate;
	@UpdateTimestamp
	@Column(name = "updatedate")
	private Timestamp updateDate;
	@ManyToOne
	private UserVO user;
}
