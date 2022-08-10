package com.kosta.uyeonhi.matching;

import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;

public interface MatchingGradeRepository extends CrudRepository<matchingGrade, Long>{
	List<matchingGrade> findByUserOrderByGradeDesc(UserVO user);
}
