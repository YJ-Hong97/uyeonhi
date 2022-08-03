package com.kosta.uyeonhi.sns;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface BoardRepository extends PagingAndSortingRepository<Board, Long>{
	
	@Query(value = "SELECT b FROM board b where b.board_id = ?1", nativeQuery = true)
	Board findByBoardId(Long boardId);
	
	List<Board> findByTagContaining(String keyword);
	
}
