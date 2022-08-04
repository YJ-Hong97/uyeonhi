package com.kosta.uyeonhi.reply;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kosta.uyeonhi.sns.Board;
import com.kosta.uyeonhi.sns.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ReplyController {
	
	private final ReplyService replyService;
	
	private final BoardService boardService;
	
	@GetMapping(value = "/reply/{boardId}")
	public String replyList(@PathVariable Long boardId, Model model) {
		Board board = boardService.findByBoardID(boardId);
		List<ReplyResponseDTO> replyList = replyService.getReplyList(board);
		model.addAttribute("board", board);
		model.addAttribute("replyList", replyList);
		return "sns/replyList";
	}
}
