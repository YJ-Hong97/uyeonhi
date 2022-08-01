package com.kosta.uyeonhi.signUp;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface IdealRepository extends CrudRepository<IdealTypeVO, Long>{
	List<IdealTypeVO> findByuserId(String userId);
	@Transactional
	@Modifying
	void deleteByuserId(String userId);
}
