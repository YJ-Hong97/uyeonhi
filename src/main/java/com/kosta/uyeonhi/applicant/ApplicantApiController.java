package com.kosta.uyeonhi.applicant;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApplicantApiController {
	
	private final ApplicantRepository applicantrepository;
	
	@Transactional
	@PostMapping("/sns/applicant/{board_id}")
	public ResponseEntity<?> applicant(@PathVariable Long board_id){
		System.out.println(board_id);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
		
        applicantrepository.applicant(board_id , username );
		return new ResponseEntity<>("신청 성공",HttpStatus.OK);
		
	}
	
	@Transactional
	@DeleteMapping("/sns/applicantCancle/{board_id}")
	public ResponseEntity<?> applicantCancle(@PathVariable Long board_id){
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
		
        applicantrepository.applicantCancle(board_id , username );
		return new ResponseEntity<>("신청 취소 성공",HttpStatus.OK);
		
	}

}
