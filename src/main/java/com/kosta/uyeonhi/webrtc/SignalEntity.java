package com.kosta.uyeonhi.webrtc;



import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamicInsert
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignalEntity {
	@Id
	private Long signalId;
	private String type;
	private String sdp;
}
