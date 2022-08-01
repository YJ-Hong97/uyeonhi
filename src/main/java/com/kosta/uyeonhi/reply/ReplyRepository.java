package com.kosta.uyeonhi.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kosta.uyeonhi.sns.Board;

public interface ReplyRepository extends JpaRepository<Reply, String>{
		
	@Query(value ="SELECT r FROM reply r where r.board_board_id =: ?1", nativeQuery = true)
	List<Reply> getRepliesByBoardId(Long boardId);

}
