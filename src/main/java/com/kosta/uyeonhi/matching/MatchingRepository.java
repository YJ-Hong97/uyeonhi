package com.kosta.uyeonhi.matching;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface MatchingRepository extends CrudRepository<MatchingVO, Long> {

	public List<MatchingVO> findByTargetAndMconfirm(String target , int mConfirm);
	
	 //select id from matching where target ='pung' and m_confirm =0;
	
	@Modifying
	@Query(value = "update matching set mConfirm = 1 where id = ?1 and target = ?2", nativeQuery = true)
	public int modifyMatching(String id , String target);
	
	@Modifying
	@Query(value = "delete from matching where id = ?1 and target = ?2", nativeQuery = true)
	public int deletMatching(String id , String target);
	
}
