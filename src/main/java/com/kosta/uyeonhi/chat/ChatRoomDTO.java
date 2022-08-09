package com.kosta.uyeonhi.chat;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity @Table(name = "tbl_room")
public class ChatRoomDTO {
	@Id
	private String roomId;
    private String name;
    //WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션
    
    @ManyToOne
    private UserVO mid;
    
    @ManyToOne
    private UserVO target;
    
	@OneToMany(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY )
    @JoinColumn(name = "roomId")
    private List<ChatMessageDTO> messages;
}









