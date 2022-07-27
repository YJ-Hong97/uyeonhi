package com.kosta.uyeonhi.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long>{

	@Modifying
	@Query(value = "insert into likes (board_board_id, user_id) values (:boardId,:userId)", nativeQuery = true)
	void likes(long boardId, String userId);
	
	@Modifying
	@Query(value = "DELETE FROM likes WHERE board_board_id = :boardId AND user_id = :userId;", nativeQuery = true)
	void notLikes(long boardId, String userId);
	
}
