package com.kosta.uyeonhi.sns;

import java.sql.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kosta.uyeonhi.reply.Reply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sns {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long board_id;

	@Column(nullable = false, length = 255)
	private String content;
	
	@Column(nullable = true)
	private String image_path;
	
	@Column(nullable = true)
	private String video_path;
	
	@CreationTimestamp
	private Date regdate;
	
	@LastModifiedDate
	private Date update;
	
	@OneToMany(mappedBy = "sns", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Reply> reply;
	
	@Column(nullable = false)
	private String board_type;
	
	private int total_person;
	
	private int applicant_person;
	
	private Date deadline;
}
