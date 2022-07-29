package com.kosta.uyeonhi.follow;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends PagingAndSortingRepository<Follow, Long> {
	@Query(value = "select * from follow f where f.target=?1 and f.user_id =?2", nativeQuery = true)
	Follow checkFollow(String tid, String mid);
	
	@Query(value = "select COUNT(*) from follow WHERE target = ?1", nativeQuery = true)
	int countFollower(String tid);
	
	@Query(value = "select COUNT(*) from follow WHERE user_id = ?1", nativeQuery = true)
	int countFollowing(String mid);
}
