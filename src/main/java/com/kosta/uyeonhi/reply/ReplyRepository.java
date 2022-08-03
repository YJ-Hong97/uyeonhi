package com.kosta.uyeonhi.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kosta.uyeonhi.sns.Board;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
		
	List<ReplyResponseDTO> findByBoard(Board board);

	@Query(value = "SELECT * FROM reply r where reply_id = ?1", nativeQuery = true)
	Reply findByReplyId(Long boardId);

}
