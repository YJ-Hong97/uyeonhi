package com.kosta.uyeonhi.mypage;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kosta.uyeonhi.signUp.UserVO;

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
@Table
public class ChattingRoomVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long roomNo;
	private String title;
	private ChatType type;
	@ManyToOne
	private UserVO user;
	@Override
	public int hashCode() {
		return Objects.hash(roomNo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChattingRoomVO other = (ChattingRoomVO) obj;
		return Objects.equals(roomNo, other.roomNo);
	}
	
	
}
