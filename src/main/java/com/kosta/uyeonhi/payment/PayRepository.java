package com.kosta.uyeonhi.payment;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;


public interface PayRepository extends CrudRepository<PayVO, Long>{
	public List<PayVO> findByUser(UserVO user);
}