package com.kosta.uyeonhi.VO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "ideal_menu")
public class IdealMenuVO {
	@Id
	private Long idealId;
	@Column(nullable = false)
	@NonNull
	private String idealValue;
}
