package com.kosta.uyeonhi.voiceChat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/face")
@Log4j2
public class VoiceChatCotroller {

	@GetMapping(value = "/rtc")
	public void rtc() {
		log.info("왓냐");
	}
}
