package com.kosta.uyeonhi.chat;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatMessageRepository extends PagingAndSortingRepository<ChatMessageDTO, Long> {
	
	@Query(value = "select * from tbl_message m where room_id = ?1 order by regdate", nativeQuery = true)
	List<ChatMessageDTO> getMesseges(String room_id);
}
