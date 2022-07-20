package com.kosta.uyeonhi.payment;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "promise")
public class PayVO {
	
	@Id
	@GeneratedValue( strategy =GenerationType.AUTO)
	private Long  promise_id;
	
	@Column(length = 1000)
	private String location;
	
	@UpdateTimestamp
	private Timestamp time;
	
	@Column(length = 1)
	private int promise_ox;
	private int cancel_ox;
	
	private int room_no;
	
	@Column(length = 400)
	private String reason;
}
