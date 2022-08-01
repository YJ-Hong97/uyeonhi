package com.kosta.uyeonhi.signUp;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;


public interface FavoriteRepository extends CrudRepository<FavoriteVO, Long>{

	List<FavoriteVO> findByuserId(String id);
	@Transactional
	@Modifying
	void deleteByUserId(String userId);
}
