package com.kosta.uyeonhi.signUp;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.*;

public interface UserRepository extends CrudRepository<UserVO, String>{
	boolean existsByNickname(String nick);
	boolean existsByEmail(String email);
	
}
