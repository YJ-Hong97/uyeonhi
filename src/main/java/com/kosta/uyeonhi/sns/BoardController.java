package com.kosta.uyeonhi.sns;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kosta.uyeonhi.chat.ChatRoomRepository;
import com.kosta.uyeonhi.likes.LikesRepository;
import com.kosta.uyeonhi.push.NotificationService;
import com.kosta.uyeonhi.reply.ReplyService;
import com.kosta.uyeonhi.signUp.ProfileRepository;
import com.kosta.uyeonhi.signUp.ProfileType;
import com.kosta.uyeonhi.signUp.ProfileVO;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

//@Controller  @RestController 
//@RestController
@Controller
@RequestMapping("/sns/")
@Log4j2
@AllArgsConstructor
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	BoardService boardService;
	@Autowired
	ReplyService replyService;
	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	NotificationService notificationService;
	
	
	@Autowired
	ChatRoomRepository roomrepo;

	@GetMapping("/sns1")
	public void boardList(Model model,
			@PageableDefault(size = 10, sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable,
			HttpSession session) {
		int startPage = ((pageable.getPageNumber() - 1) / 10) * 10 + 1;
		pageable.getPageSize();
		int endPage = startPage + 10 - 1 > pageable.getPageSize() ? pageable.getPageSize() : startPage + 10 - 1;
		UserVO user = (UserVO) session.getAttribute("user");
		ProfileVO profile =profileRepository.findByUserAndType(user, ProfileType.MAIN);
				
		model.addAttribute("notiCount", notificationService.notificationCount(user.getId()));
		model.addAttribute("list", roomrepo.findByTargetOrId(user.getId()));
		model.addAttribute("user", user);
		model.addAttribute("boardList", boardService.pageList(pageable));
		model.addAttribute("profile", profile);

		// boardService.pageList().get(0).getWriter().getId());

		// return boardRepository.findAll(pageable);
	}

	//검색으로
		@GetMapping("/search")
		public String search(String tag, Model model) {
			System.out.println("controller : "+tag);		
			List<Board> searchList = boardService.tagSearch(tag);
			
			if(searchList.size()==0) {
				String message = "검색 결과가 없습니다";
				model.addAttribute("msg",message);
			}
			model.addAttribute("searchList",searchList);
			System.out.println(searchList.size());
		 
			return "sns/search";
		}
	
	//태그눌러서
	@PostMapping("/clickTag")
	public String clickTag(String tag, Model model) {
		System.out.println("controller : "+tag);
		List<Board> searchList = boardService.tagSearch(tag);
		model.addAttribute("searchList",searchList);
		System.out.println(searchList.size());
	 
		return "sns/search";
	}
	
	@GetMapping("/likeList")
	public String LikeList(HttpSession session, Model model) {
		UserVO user = (UserVO) session.getAttribute("user");	
		System.out.println(user.getId());
		List<Board> likeList = boardService.likeList(user.getId());
		
		model.addAttribute("likeList",likeList);
	 
		return "sns/likeList";
	}
	
	@GetMapping("/{boardId}")
	public String boardDetail(@PathVariable Long boardId, Model model) {
		System.out.println(boardId);
		Board boardDetail = boardService.findByBoardID(boardId);
		System.out.println(boardDetail);
		
		model.addAttribute("boardDetail",boardDetail);
		return "sns/boardDetail";
	}
	
	@ResponseBody
	@GetMapping("/modal/{boardId}")
	public Board modalBoard(@PathVariable Long boardId, Model model) {
		Board boardDetail = boardService.findByBoardID(boardId);
		return boardDetail;
	}
	
	
	
	

}
