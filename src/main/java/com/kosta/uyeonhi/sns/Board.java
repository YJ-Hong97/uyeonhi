package com.kosta.uyeonhi.sns;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kosta.uyeonhi.likes.Likes;
import com.kosta.uyeonhi.reply.Reply;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@Table(name="Board")
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardId;

	@Column(nullable = false, length = 255)
	private String content;
	
	@Column(nullable = true)
	private String image_path;
	
	@Column(nullable = true)
	private String video_path;
	
	@CreationTimestamp
	private LocalDateTime regdate;
	
	@LastModifiedDate
	@Column(nullable = true)
	private LocalDateTime updateDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private UserVO writer;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "reply_id")
	private List<Reply> reply;
	
	@Column(nullable = false)
	private String board_type;
	
	@Column(nullable = true)
	private int total_person;
	
	@Column(nullable = true)
	private int applicant_person;
	
	@Column(nullable = true)
	private Date deadline;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Likes> likes;
}
