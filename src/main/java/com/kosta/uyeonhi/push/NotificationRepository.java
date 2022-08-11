package com.kosta.uyeonhi.push;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	@Query(value = "select count(*) from notification where user_id = ?1 AND is_read = 0", nativeQuery = true)
	public int alarmCount(String receiverId);

	@Query(value = "select * from notification where user_id = ?1 order by notification_id DESC", nativeQuery = true)
	public List<Notification> selectNotiByReceiverId(String receiverId);

	@Query(value = "UPDATE notification  SET is_read = 1 where user_id = ?1", nativeQuery = true)
	public void updateSelectByUserName(String userId);
}
