package com.kosta.uyeonhi.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j2;

//@Controller  @RestController 
//@RestController
@Controller
@RequestMapping("/sns/")
@Log4j2
public class BoardController {
	
	@Autowired
	private  BoardRepository boardRepository;
	
	@Autowired
	private  BoardService boardService;
	
	@GetMapping("/sns1")
	public void boardList(Model model, @PageableDefault(size=24, sort="id",
            direction = Sort.Direction.DESC) Pageable pageable) {
		
		int startPage = ((pageable.getPageNumber()-1) / 10) * 10 + 1;
	    pageable.getPageSize();
	    int endPage = startPage + 10 - 1  > pageable.getPageSize() ? pageable.getPageSize() : startPage + 10 - 1;
	    model.addAttribute("startPageNo", startPage);
	    model.addAttribute("endPageNo", endPage);
	    
	    List<Board> blist =  boardService.pageList();
		System.out.println(blist);
		 model.addAttribute("boardList", blist);
		// model.addAttribute("likeList", boardService.likeList());
		//boardService.pageList().get(0).getWriter().getId());
		
		//return boardRepository.findAll(pageable);
	}
	
	/*@GetMapping("/boardWrite")
	public String boardWirteGet() {
		return "sns1";
	}*/
	
	
}
