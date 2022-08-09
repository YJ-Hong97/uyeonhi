package com.kosta.uyeonhi.applicant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kosta.uyeonhi.likes.Likes;
import com.kosta.uyeonhi.payment.PayVO;
import com.kosta.uyeonhi.reply.ReplyService;
import com.kosta.uyeonhi.signUp.ProfileRepository;
import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;
import com.kosta.uyeonhi.sns.BoardRepository;
import com.kosta.uyeonhi.sns.BoardService;
import com.kosta.uyeonhi.util.PageMaker;
import com.kosta.uyeonhi.util.PageVO;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Controller
/* @AllArgsConstructor */
public class AutoController {


	
	/* private final TagService TagService; */
	@Autowired
	TagService TagService;


	@GetMapping("/autocomplete")
	public void autocomplete(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
	
	    String searchValue = request.getParameter("searchValue"); 
	    JSONArray arrayObj = TagService.autoSearch(searchValue);
		
	    response.setCharacterEncoding("UTF-8");
	    
	    PrintWriter pw = response.getWriter(); 
	    pw.print(arrayObj); 
	    pw.flush(); 
	    pw.close();
		
		
	
	}
	
	

}
