package com.kosta.uyeonhi.signUp;

import java.sql.Date;



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
@Table(name = "favorite")
public class FavoriteVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long favoriteId;
	@ManyToOne
	private FavoriteMenuVO favorite;
	@ManyToOne
	private UserVO user;
}
