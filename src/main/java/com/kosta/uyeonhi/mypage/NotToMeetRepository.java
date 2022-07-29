package com.kosta.uyeonhi.mypage;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.kosta.uyeonhi.signUp.UserVO;


public interface NotToMeetRepository extends CrudRepository<NotToMeetVO, Long>{

	List<NotToMeetVO> findByUser(UserVO user);
	@Transactional
	@Modifying
	@Query(value = "delete from NotToMeetVO where user = :user and phone = :phone")
	void deleteBlock(@Param("user") UserVO user,@Param("phone") String phone);
	boolean existsByPhoneAndUser(String phone,UserVO user);
}
