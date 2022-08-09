package com.kosta.uyeonhi.matching;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;


public interface MatchingRepository extends CrudRepository<MatchingVO, Long> {

	public List<MatchingVO> findByTargetAndMconfirm(UserVO target , int mConfirm);
	
	 //select id from matching where target ='pung' and m_confirm =0;
	
	@Query(value = "select * from matching m where (m.id_id = ?1 and m.target_id = ?2) OR (m.id_id = ?2 and m.target_id = ?1)", nativeQuery = true)
	MatchingVO matcheck(String id, String target);
	
	@Modifying
	@Query(value = "update matching set mConfirm = 1 where id_id = ?1 and target_id = ?2", nativeQuery = true)
	public int modifyMatching(String id , String target);
	
	@Modifying
	@Query(value = "delete from matching where id_id = ?1 and target_id = ?2", nativeQuery = true)
	public int deletMatching(String id , String target);
	
}
