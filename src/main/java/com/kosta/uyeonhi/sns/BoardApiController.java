package com.kosta.uyeonhi.sns;


import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
public class BoardApiController {
	
	private final BoardRepository boardRepository;
	
	private final UserRepository userRepository;
	
	private final BoardService boardService;

	private final BoardS3Uploader boardS3Uploader;
	
	@PostMapping("/api/sns/boardWrite")
	public long boardWritePost(@RequestPart(value = "key") BoardRequestDTO boardDTO, 
			@RequestPart(value="file") MultipartFile[] files ,HttpSession session) throws IOException {
        UserVO user = (UserVO)session.getAttribute("user");
        String imageURL = "";
        for(int i = 0; i < files.length; i++) {
        	imageURL += boardS3Uploader.upload(files[i], "board");
        	imageURL += ",";
        }
        boardDTO.setImage_path(imageURL);
        boardDTO.setWriter(user);
        return boardService.saveBoard(boardDTO);
	}
	
	@DeleteMapping("/api/sns/boardDelete/{board_id}")
	public Long delete(@PathVariable Long board_id) {
		boardService.delete(board_id); 
		return board_id;
	}
	
	@PutMapping("/api/sns/boardUpdate/{board_id}")
	public Long update(@PathVariable Long board_id, @RequestBody BoardRequestDTO boardDTO) {
		System.out.println(board_id + boardDTO.toString());
		boardService.update(board_id, boardDTO);
		return board_id;
	}

	/*
	 * @PutMapping("/api/sns/recruitApply/{board_id}") public Long
	 * recruitApply(@PathVariable Long board_id,@RequestBody BoardRequestDTO
	 * boardDTO) { boardService.recruitApply(board_id, boardDTO); return board_id; }
	 */
}
