package com.kosta.uyeonhi.push;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.uyeonhi.signUp.UserVO;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class NotificationApiController {

	private final NotificationServiceImpl notificationService;
	
	@PostMapping("/api/notification/save")
	public void saveNoti(@RequestBody NotificationRequestDTO notificationRequestDTO) {
		int alram = 1;
		notificationService.notificationSave(notificationRequestDTO);
		
	}
	
	@DeleteMapping("/api/notification/delete/{notificationId}")
    public void delete(@PathVariable Long notificationId){
		notificationService.notificationDelete(notificationId);
    }
	
	
}
