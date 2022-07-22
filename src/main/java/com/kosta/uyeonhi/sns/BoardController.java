package com.kosta.uyeonhi.sns;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import groovyjarjarpicocli.CommandLine.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sns/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
	
	
	private final BoardService boardService;
	
	@GetMapping("sns1")
	public void viewSNS(Model model) {
		/* List<BoardDTO> list = */
	}
	
	@PostMapping("/boardWrite")
	public String boardWrite(String contents) throws Exception{
		boardService.saveBoard(null);
		return "redirect:/sns1";
	}
}
