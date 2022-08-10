package com.kosta.uyeonhi.likes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;

public interface LikesRepository extends JpaRepository<Likes, Long> {

	@Modifying
	@Query(value = "insert into likes (board_id,user_id) values (?1,?2)", nativeQuery = true)
	void likes(Long board_id , String userId );

	@Modifying
	@Query(value = "DELETE FROM likes WHERE board_id = ?1 and user_id =?2", nativeQuery = true)
	void notLikes(Long board_id , String userId );

	
	/*
	 * @Modifying
	 * 
	 * @Query(value =
	 * "insert into likes (board_board_id, user_id) values (:board_id,:userId)",
	 * nativeQuery = true) void likes(Long board_id , String userId );
	 */

	/*
	 * @Modifying
	 * 
	 * @Query(value =
	 * "DELETE FROM likes WHERE board_board_id = :board_id AND user_id = :userId;",
	 * nativeQuery = true) void notLikes(Long board_id , String userId );
	 */

}
