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
	private Long mId; 
	
	@ManyToOne
	private UserVO id; 
	
	@ManyToOne
	private UserVO target; 
	
	private int mconfirm; 
	private String type;
	
}
