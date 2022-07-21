package com.kosta.uyeonhi.sns;

import java.sql.Date;
import java.time.LocalDateTime;

import com.kosta.uyeonhi.signUp.UserVO;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
	private Long board_id;
	
	private String content;
	
	private String board_type;
	
	private Date deadline;
	
	private String image_path;
	
	private String video_path;
	
	private int applicant_person;
	
	private int total_person;
	
	private LocalDateTime regdate;
	
	private LocalDateTime update_date;
	
	private String writerNickname;
	
	private String writerEmail;
	
	private int replyCount;
}
