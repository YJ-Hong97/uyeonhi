package com.kosta.uyeonhi.sns;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BoardRepository extends PagingAndSortingRepository<Board, Long>{
	
	@Query(value = "SELECT b FROM board b where b.board_id = ?1", nativeQuery = true)
	Board findByBoardId(Long boardId);
}
