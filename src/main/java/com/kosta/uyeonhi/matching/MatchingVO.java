package com.kosta.uyeonhi.matching;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kosta.uyeonhi.signUp.UserVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "matching")
public class MatchingVO {

	@Id
	@GeneratedValue( strategy =GenerationType.AUTO)
	private Long mId; //key값
	
	@ManyToOne
	private UserVO id; //신청 한 아이디 //신청인 여기다 넣고
	
	@ManyToOne
	private UserVO target; //요청받은 아이디 //요청인 아이디에 넣기
	
	private int mconfirm; //확인 컬
	private String type;
	
}
