package com.kosta.uyeonhi.follow;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kosta.uyeonhi.signUp.UserVO;

@Repository
public interface FollowRepository extends PagingAndSortingRepository<Follow, Long> {
	@Query(value = "select * from follow f where f.target_id=?1 and f.user_id =?2", nativeQuery = true)
	Follow checkFollow(String tid, String mid);
	
	@Query(value = "select COUNT(*) from follow WHERE target_id = ?1", nativeQuery = true)
	int countFollower(String tid);
	
	@Query(value = "select COUNT(*) from follow WHERE user_id = ?1", nativeQuery = true)
	int countFollowing(String mid);
	
	List<Follow> findByTarget(UserVO target);
	
	List<Follow> findByUser(UserVO user);
}
