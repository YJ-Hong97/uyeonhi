package com.kosta.uyeonhi.promise;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;

public interface PromiseRepository extends CrudRepository<PromiseVO, Long>{
	public List<PromiseVO> findByMe(UserVO me);
}
