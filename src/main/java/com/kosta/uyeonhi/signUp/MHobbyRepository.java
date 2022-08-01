package com.kosta.uyeonhi.signUp;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface MHobbyRepository extends CrudRepository<MHobbyVO, Long>{
	List<MHobbyVO> findByUser(UserVO user);
	@Transactional
	@Modifying
	void deleteByUser(UserVO user);
}
