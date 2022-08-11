package com.kosta.uyeonhi.promise;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;

public interface PromiseRepository extends CrudRepository<PromiseVO, Long>{
	public List<PromiseVO> findByMe(UserVO me);

	@Query(value ="select * from promise where me_id = ?1 and hourmin = STR_TO_DATE(?2,'%Y-%m-%d %H:%i:%S') "
			+ "and title like %?3%", nativeQuery = true)
	PromiseVO selectByDetail(String me_id, String hourmin,String title);
	
	@Query(value = "select count(*) from promise p where MONTH(hourmin)= ?1 and me_id = ?2 and promise_ox != 'x'", nativeQuery = true)
	int selectByMonth(int month, String me_id);
}
