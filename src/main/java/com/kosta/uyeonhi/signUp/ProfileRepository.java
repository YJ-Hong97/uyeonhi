package com.kosta.uyeonhi.signUp;


import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<ProfileVO, Long>{
	ProfileVO findByUserAndType(UserVO user, ProfileType type);
}
