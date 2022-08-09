package com.kosta.uyeonhi.signUp;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.kosta.uyeonhi.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RequiredArgsConstructor //nonnull인것만 가지고 생성
@Entity
@Table(name = "tbl_user")
public class UserVO  implements Serializable{
	
	@Id
	private String id;//기본형 타입은 안됨
	@NonNull//생성시 조건(lombok)
	@Column(nullable = false)//db notnull(jpa)
	private String password;
	@NonNull//생성시 조건(lombok)
	@Column(nullable = false)//db notnull(jpa)
	private String name;
	@NonNull//생성시 조건(lombok)
	@Column(nullable = false)//db notnull(jpa)
	private String nickname;
	@NonNull//생성시 조건(lombok)
	@Column(nullable = false)//db notnull(jpa)
	private String phone;
	@NonNull//생성시 조건(lombok)
	@Column(nullable = false)//db notnull(jpa)
	private String email;
	
	@NonNull//생성시 조건(lombok)
	@Column(nullable = false)//db notnull(jpa)
	private Gender gender;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ProfileVO> profile;
	
	@NonNull//생성시 조건(lombok)
	@Column(nullable = false, name = "maching_confirm")//db notnull(jpa)
	private String machingConfirm;
	@NonNull//생성시 조건(lombok)
	@Column(nullable = false)//db notnull(jpa)
	private Date birth;
	private int coin;
	private int push;
	private String kakao;
	
}
