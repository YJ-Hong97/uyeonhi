package com.kosta.uyeonhi.signUp;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserVO, String>{
	boolean existsByNickname(String nick);
	boolean existsByEmail(String email);
	@Transactional
	@Modifying
	void deleteById(String userId);
	List<UserVO> findByGender(Gender gender);
	UserVO findByPhone(String phone);
}
