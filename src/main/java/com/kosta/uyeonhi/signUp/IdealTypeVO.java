package com.kosta.uyeonhi.signUp;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Entity
@Table(name = "ideal_type")
public class IdealTypeVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idealId;
	@ManyToOne
	private IdealMenuVO ideal;
	@ManyToOne
	private UserVO user;
}
