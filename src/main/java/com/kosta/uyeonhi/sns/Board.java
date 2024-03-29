package com.kosta.uyeonhi.sns;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kosta.uyeonhi.applicant.Applicant;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private UserVO writer;
	
	@JsonIgnore
	@OneToMany(mappedBy = "board",  fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Reply> reply;
	
	@Column(nullable = false)
	private String board_type;
	
	@Column(nullable = true)
	private int total_person;
	
	@Column(nullable = true)
	private int applicant_person;
	
	@Column(nullable = true)
	private Date deadline;
	
	@JsonIgnore
	@OneToMany(  fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "board_id") 
	private List<Likes> likes;
	
	@Column(nullable = true)
	private String tag;
	
	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<Applicant> applicant;
	
	
}





