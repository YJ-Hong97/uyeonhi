package com.kosta.uyeonhi.signUp;

import org.springframework.data.repository.CrudRepository;


import com.kosta.uyeonhi.*;

public interface UserRepository extends CrudRepository<UserVO, String>{
	boolean existsByNickname(String nick);
}
