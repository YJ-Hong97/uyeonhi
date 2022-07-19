package com.kosta.uyeonhi.repository;

import org.springframework.data.repository.CrudRepository;


import com.kosta.uyeonhi.*;
import com.kosta.uyeonhi.VO.UserVO;

public interface UserRepository extends CrudRepository<UserVO, String>{
	boolean existsByNickname(String nick);
}
