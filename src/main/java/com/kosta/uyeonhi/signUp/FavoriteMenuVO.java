package com.kosta.uyeonhi.signUp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "favorite_menu")
public class FavoriteMenuVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favortieId;
	@Column(nullable = false)
	@NonNull
	private String favoriteValue;
}
