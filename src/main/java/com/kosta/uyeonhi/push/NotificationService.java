package com.kosta.uyeonhi.push;

import java.util.List;

public interface NotificationService {
	
	void notificationSave(NotificationRequestDTO notificationRequestDTO);
	
	int notificationCount(String receiverId);
	
	List<Notification> getNotiList(String receiverId);
	
	void notificationDelete(Long notificationId);
	
	void notificationUpdate(String userId);
}
