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
public class BoardResponseDTO {
	private Long boardId;
	
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
	
	//repository를 통해 조회한 entity를 dto로 변환하는 용도.
	public BoardResponseDTO(Board board){
		this.boardId = board.getBoardId();
		this.content = board.getContent();
		this.deadline = board.getDeadline();
		this.board_type = board.getBoard_type();
		this.image_path = board.getImage_path();
		this.video_path = board.getVideo_path();
		this.applicant_person = board.getApplicant_person();
		this.total_person = board.getTotal_person();
		this.regdate = board.getRegdate();
		this.update_date = board.getUpdateDate();
		this.writerNickname = board.getWriter().getNickname();
		this.writerEmail = board.getWriter().getEmail();
	}
}
