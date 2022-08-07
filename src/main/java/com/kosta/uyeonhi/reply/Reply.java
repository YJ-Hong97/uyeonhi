package com.kosta.uyeonhi.reply;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javax.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@DynamicInsert
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"user","board","parent"})
public class Reply{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reply_id;
	
	@Column(nullable = false, length = 30)
	private String reply_content;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private UserVO user;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Board board;
	
	@CreationTimestamp
	private LocalDateTime regdate;
	
	@LastModifiedDate
	@Column(nullable = true)
	private LocalDateTime updateDate;

	@ColumnDefault("0")	
	private String depth;
	
	private int reply_like;
	
	private boolean isRemoved= false;
	
	@OneToOne(fetch = FetchType.EAGER) //49~54 자기참조
    @JoinColumn(name = "parent_id", nullable = true)
    private Reply parent;

	@JsonIgnore
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Reply> children = new ArrayList<>();
}
