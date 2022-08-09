package com.kosta.uyeonhi.applicant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kosta.uyeonhi.likes.Likes;
import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;

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
@Table(name = "applicant")
public class Applicant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int applicant_id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private UserVO user;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Board board;

}
