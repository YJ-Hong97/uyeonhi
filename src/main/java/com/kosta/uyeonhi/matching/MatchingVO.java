package com.kosta.uyeonhi.matching;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private String m_id;
	
	private String id;
	
	private String target;
	private long m_confirm;
	private String type;
	
}
