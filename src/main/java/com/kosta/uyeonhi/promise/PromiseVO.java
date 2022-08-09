package com.kosta.uyeonhi.promise;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.kosta.uyeonhi.signUp.UserVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name= "promise")
public class PromiseVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long proId;
	
	String location;

	String title;
	
	@UpdateTimestamp
	Timestamp time;
	
	@ManyToOne
	UserVO me;
	
	@ManyToOne
	UserVO you;
	
	String reason;
	
	@Column(length = 1)
	String promise_ox;
	
	@Column(length = 1)
	String cancel_ox;
}
