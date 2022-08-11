package com.kosta.uyeonhi.matching;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;

public interface MatchingGradeRepository extends CrudRepository<matchingGrade, Long>{
	List<matchingGrade> findByUserOrderByGradeDesc(UserVO user);
	@Transactional
	@Modifying
	void deleteByUser(UserVO user);
	matchingGrade findByUser(UserVO user);
}
