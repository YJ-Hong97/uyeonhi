package com.kosta.uyeonhi.matching;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "matchingGrade")
public class matchingGrade {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long mid;
	@ManyToOne
	private UserVO user;
	@ManyToOne
	private UserVO target;
	private int grade;
	@CreationTimestamp
	private Timestamp makeTime;
	private int block;
}
