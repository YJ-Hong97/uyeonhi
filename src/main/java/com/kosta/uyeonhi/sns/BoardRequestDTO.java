package com.kosta.uyeonhi.sns;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import com.kosta.uyeonhi.reply.Reply;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardRequestDTO {

	private Long board_id;

	private String content;

	private String image_path;

	private String video_path;

	private LocalDateTime regdate;

	private LocalDateTime updateDate;

	private UserVO writer;

	private List<Reply> reply;

	private String board_type;

	private int total_person;

	private int applicant_person;

	private Date deadline;

	@Builder
	public BoardRequestDTO(Long board_id, String content, UserVO writer, String board_type) {
		this.board_id = board_id;
		this.content = content;
		this.writer = writer;
		this.board_type = board_type;
	}

	public Board toEntity() {
		return Board.builder()
				.boardId(board_id)
				.content(content)
				.board_type(board_type)
				.writer(writer)
				.build();
	}

}
