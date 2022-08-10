package com.kosta.uyeonhi.chat;


import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kosta.uyeonhi.signUp.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping(value = "/chat")
@RequiredArgsConstructor
@Log4j2
public class RoomController {
	
	@Autowired
	ChatRoomRepository roomrepo;
	
	@Autowired
	ChatMessageRepository chatRepo;
	
	@Autowired
	UserRepository uRepo;

	@GetMapping(value = "/rooms")
	public ModelAndView rooms(Principal principal, HttpSession session) {
		String mid = principal.getName();
		
		ModelAndView mv = new ModelAndView("chat/rooms");
		mv.addObject("list", roomrepo.findByTargetOrId(mid));
		
		return mv;
	}

	@PostMapping(value = "/room")
	public String create(ChatRoomDTO chatroom, RedirectAttributes rttr) {
		roomrepo.save(chatroom);
		
		rttr.addFlashAttribute("roomName", chatroom);
		
		return "redirect:/chat/rooms";
	}
	
	@GetMapping(value = "/doChatt")
	public String createrooms(String mid, String tid, RedirectAttributes rttr) {
		ChatRoomDTO chatroom = roomrepo.getroom(mid, tid);
		
		if(chatroom == null) {
			chatroom = ChatRoomDTO.builder()
					.roomId(mid + tid)
					.name(tid+mid)
					.mid(uRepo.findById(mid).get())
					.target(uRepo.findById(tid).get())
					.build();
			roomrepo.save(chatroom);
		}
		String roomid = chatroom.getRoomId();

		return "redirect:/chat/room?roomId="+roomid;
	}
	
	@GetMapping("/room")
    public void getRoom(String roomId, Model model){
		List<ChatMessageDTO> messages = chatRepo.getMesseges(roomId);
		
		model.addAttribute("messages", messages);
        model.addAttribute("room", roomrepo.findByRoomId(roomId));
    }
}










