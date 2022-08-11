package com.kosta.uyeonhi.matching;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;

public interface MatchingRepository extends CrudRepository<MatchingVO, Long> {
	
	//매칭 요청하기
	public List<MatchingVO> findByTargetAndMconfirm(UserVO target, int mConfirm);
	//받은 매칭 확인
	public List<MatchingVO> findByIdAndMconfirm(UserVO target, int mConfirm);
	
	//매칭 신청 체크
	@Query(value = "select * from matching m where (m.id_id = ?1 and m.target_id = ?2) OR (m.id_id = ?2 and m.target_id = ?1)", nativeQuery = true)
	MatchingVO matcheck(String id, String target);
	
	//매칭 완료 목록
	@Query(value = "select * from matching m where (m.id_id = ?1 or m.target_id = ?1) and m.mconfirm = 1", nativeQuery = true)
	List<MatchingVO> matSuccess(String id);
	
	//매칭 요청 수락
	@Modifying
	@Query(value = "update matching set mConfirm = 1 where id_id = ?1 and target_id = ?2", nativeQuery = true)
	public int modifyMatching(String id, String target);
	
	//매칭 요청 거절
	@Modifying
	@Query(value = "delete from matching where id_id = ?1 and target_id = ?2", nativeQuery = true)
	public int deletMatching(String id, String target);

	//내가 보낸 매칭
	List<MatchingVO> findById(UserVO id);
	
	//매칭 요청 취소
	@Modifying
	@Query(value = "delete from matching where id_id = ?1 and target_id = ?2", nativeQuery = true)
	public List<MatchingVO> cancelMatching(String id, String target);

	//매칭 끊기
	@Modifying
	@Query(value = "delete from matching where ((id_id = ?1 and target_id = ?2) OR (id_id = ?2 and target_id = ?1)) and mconfirm = 1", nativeQuery = true)
	public List<MatchingVO> matDelete(String id, String target);

}
