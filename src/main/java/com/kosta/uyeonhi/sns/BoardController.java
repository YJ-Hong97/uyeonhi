package com.kosta.uyeonhi.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import com.kosta.uyeonhi.signUp.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sns/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
	
	@Autowired
	private final BoardRepository boardRepository;
	private final BoardService boardService;
	
	@GetMapping("/sns1")
	public Page<Board> boardList(Model model, @PageableDefault(size=24, sort="id",
            direction = Sort.Direction.DESC) Pageable pageable) {
		int startPage = ((pageable.getPageNumber()-1) / 10) * 10 + 1;
	    pageable.getPageSize();
	    int endPage = startPage + 10 - 1  > pageable.getPageSize() ? pageable.getPageSize() : startPage + 10 - 1;
	    model.addAttribute("startPageNo", startPage);
	    model.addAttribute("endPageNo", endPage);		
	    model.addAttribute("boardList", boardService.pageList(pageable));
		return boardRepository.findAll(pageable);
	}
	
	@GetMapping("/board/write")
	public String boardWirteGet() {
		return "sns1";
	}
	
	@PostMapping("/boardWrite")
	public String boardWritePost(BoardDTO boardDTO) {
		boardService.saveBoard(boardDTO);
		return "redirect:/";
	}
}
