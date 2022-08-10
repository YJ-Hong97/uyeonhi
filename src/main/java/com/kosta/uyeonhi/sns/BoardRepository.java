package com.kosta.uyeonhi.sns;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface BoardRepository extends PagingAndSortingRepository<Board, Long>{
	
	@Query(value = "SELECT b FROM board b where b.board_id = ?1", nativeQuery = true)
	Board findByBoardId(Long boardId);
	
	List<Board> findByTagContaining(String tag);
	
	@Modifying
	@Query(value = "update board set applicant_person = applicant_person + 1 where board_id = ?1", nativeQuery = true)
	void recruitApply(Long boardId);
	
	@Query(value = "select b.* from board b,likes l WHERE b.board_id = l.board_id AND l.user_id = ?", nativeQuery = true)
	List<Board> likeBoardList(String userId);
	
}
