package com.kosta.uyeonhi.chat;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRoomRepository extends PagingAndSortingRepository<ChatRoomDTO, String> {
	
	public ChatRoomDTO findByRoomId(String roomId);
	
	@Query(value = "select * from tbl_room r where r.mid_id = ?1 or r.target_id = ?1", nativeQuery = true)
	public List<ChatRoomDTO> findByTargetOrId(String mid);
	
	@Query(value = "select * from tbl_room r where (r.mid_id = ?1 and r.target_id = ?2) or (r.mid_id = ?2 and r.target_id = ?1)", nativeQuery = true)
	ChatRoomDTO getroom(String mid, String tid);
}
