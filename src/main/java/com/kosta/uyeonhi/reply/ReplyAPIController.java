package com.kosta.uyeonhi.reply;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;
import com.kosta.uyeonhi.sns.BoardController;
import com.kosta.uyeonhi.sns.BoardService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
public class ReplyAPIController {
	
	private final ReplyService replyService;
	
	private final BoardService boardService;
	
	
	@PostMapping("/api/reply/{boardId}")
	public void writeReply(@PathVariable Long boardId,@RequestBody ReplyRequestDTO replyDTO, HttpSession session) {
        UserVO user = (UserVO)session.getAttribute("user");
        replyDTO.setUser(user);
        System.out.println(replyDTO.toString());
        replyService.writeReply(boardId, replyDTO);
	}
	
	@DeleteMapping("/api/reply/replyDelete/{replyId}")
	public Long delete(@PathVariable Long replyId) {
		replyService.deleteReply(replyId);
		return replyId;
	}
	
	@PutMapping("/api/reply/replyUpdate/{replyId}")
    public Long update(@PathVariable Long replyId, @RequestParam("updateCon") String updateCon){
		replyService.updateReply(replyId, updateCon);
        System.out.println("replyId:"+ replyId);
        System.out.println("replyDTO:"+ updateCon);
        return replyId;
    }
	
}
