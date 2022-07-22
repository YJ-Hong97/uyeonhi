package com.kosta.uyeonhi.payment;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "payment")
public class PayVO {
	
	@Id
	@GeneratedValue( strategy =GenerationType.AUTO)
	private Long  p_id;
	
	@Column(length = 255)
	private String id;
	
	private int uNum;
	
	@CreationTimestamp
	private Date regdate;
	
	private int amount; 
}
