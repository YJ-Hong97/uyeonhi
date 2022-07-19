package com.kosta.uyeonhi.signUp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "manager")
public class ManagerVO {
	@Id
	@Column(name = "manager_id")
	private String manager_id;
	@Column(name = "manager_pw")
	private String manager_pw;

}
