package com.kosta.uyeonhi.promise;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;

public interface PromiseRepository extends CrudRepository<PromiseVO, Long>{
	public List<PromiseVO> findByMe(UserVO me);

	@Query(value ="select * from promise where me_id = ?1 and time= ?2 and title like %?3%", nativeQuery = true)
	PromiseVO selectByDetail(String me_id, String time, String title);
}
