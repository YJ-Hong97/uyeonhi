package com.kosta.uyeonhi.push;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
	
	private final UserRepository userRepository;
	
	private final NotificationRepository notificationRepository;
	
	public void notificationSave(NotificationRequestDTO notificationRequestDTO) {
		System.out.println(notificationRequestDTO);
		UserVO receiverUser = userRepository.findById(notificationRequestDTO.getReceiverId()).orElseThrow(() -> new NullPointerException());
		Notification notification = notificationRequestDTO.toEntity();
		notification.setReceiverId(receiverUser);
		notification.setIsRead(false);
		notificationRepository.save(notification);
		
	}

	@Override
	public int notificationCount(String receiverId) {
		return notificationRepository.alarmCount(receiverId);
	}

	@Override
	public List<Notification> getNotiList(String receiverId) {
		List<Notification> notiList = notificationRepository.selectNotiByReceiverId(receiverId);
		return notiList;
	}

	@Override
	public void notificationDelete(long notificationId) {
		notificationRepository.deleteById(notificationId);		
	}

	
}
