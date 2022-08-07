package com.kosta.uyeonhi.likes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "likes")
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int like_id;

	
	@ManyToOne 
	private UserVO user;
	 
	

	//@ManyToOne
	//private Board board;

	
	/*
	 * @Transient private boolean likeStatus;
	 * @Transient private long likesCount;;
	 * 
	 */
}
