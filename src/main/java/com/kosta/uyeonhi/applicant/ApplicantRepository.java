package com.kosta.uyeonhi.applicant;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long>{

	@Modifying
	@Query(value = "insert into applicant (board_board_id, user_id) values (?1,?2)", nativeQuery = true)
	void applicant(Long board_id, String userId);
	
	@Modifying
	@Query(value = "delete from applicant where board_board_id = ?1 and user_id = ?2", nativeQuery = true)
	void applicantCancle(Long board_id, String userId);
}

