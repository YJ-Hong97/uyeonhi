package com.kosta.uyeonhi.matching;

import javax.servlet.http.HttpSession;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends CrudRepository<MatchingVO, Long>{

	@Query(value = "insert into matching (m_id , id , target) values()", nativeQuery = true)
	void matching(Long m_id , String userId );
}
