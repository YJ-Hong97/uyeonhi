package com.kosta.uyeonhi.signUp;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface MIdealRepository extends CrudRepository<MIdealVO, Long>{
	List<MIdealVO> findByUser(UserVO user);
	@Transactional
	@Modifying
	void deleteByUser(UserVO user);
}
