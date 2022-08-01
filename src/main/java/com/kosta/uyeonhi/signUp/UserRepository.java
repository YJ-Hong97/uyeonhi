package com.kosta.uyeonhi.signUp;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserVO, String>{
	boolean existsByNickname(String nick);
	boolean existsByEmail(String email);
	@Transactional
	@Modifying
	void deleteById(String userId);
}
