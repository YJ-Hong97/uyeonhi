package com.kosta.uyeonhi.VO;

import java.sql.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "ideal_type")
public class IdealTypeVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idealId;
	@Column(nullable = false)
	@NonNull
	private String user_id;
}
