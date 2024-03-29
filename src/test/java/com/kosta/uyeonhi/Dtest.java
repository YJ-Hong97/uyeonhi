package com.kosta.uyeonhi;

import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.kosta.uyeonhi.mypage.ChatType;
import com.kosta.uyeonhi.mypage.ChatUserState;
import com.kosta.uyeonhi.mypage.ChattingRoomRepository;
import com.kosta.uyeonhi.mypage.ChattingRoomVO;
import com.kosta.uyeonhi.mypage.ChattingUsersRepository;
import com.kosta.uyeonhi.mypage.ChattingUsersVO;
import com.kosta.uyeonhi.signUp.FavoriteMenuRepository;
import com.kosta.uyeonhi.signUp.FavoriteMenuVO;
import com.kosta.uyeonhi.signUp.HobbyMenuRepository;
import com.kosta.uyeonhi.signUp.HobbyMenuVO;
import com.kosta.uyeonhi.signUp.IdealMenuRepository;
import com.kosta.uyeonhi.signUp.IdealMenuVO;
import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

@SpringBootTest
public class Dtest {
	@Autowired
	UserRepository uRepo;
	@Autowired
	ChattingRoomRepository chatRoomRepo;
	@Autowired
	ChattingUsersRepository chatUserRepo;
	@Test
	public void test() {
		UserVO user = uRepo.findById("pung").get();
		ChattingRoomVO room = ChattingRoomVO.builder()
				.title("뿡뿡이와 나")
				.user(user)
				.type(ChatType.video)
				.build();
		chatRoomRepo.save(room);
		ChattingUsersVO cuser = ChattingUsersVO.builder()
				.user(user)
				.state(ChatUserState.unconnect)
				.room(room)
				.build();
		chatUserRepo.save(cuser);
		ChattingUsersVO user2 = ChattingUsersVO.builder()
				.user(uRepo.findById("hyj1077").get())
				.state(ChatUserState.unconnect)
				.room(room)
				.build();
		chatUserRepo.save(user2);
		
	}
	@Autowired
	FavoriteMenuRepository fRepo;
	@Autowired
	HobbyMenuRepository hRepo;
	@Autowired
	IdealMenuRepository iRepo;
	
	@Test
	public void insert() {
		FavoriteMenuVO fmenu = FavoriteMenuVO.builder()
				.favoriteValue("하루필름")
				.build();
		fRepo.save(fmenu);
		FavoriteMenuVO fmenu1 = FavoriteMenuVO.builder()
				.favoriteValue("맥주")
				.build();
		fRepo.save(fmenu1);
		FavoriteMenuVO fmenu2 = FavoriteMenuVO.builder()
				.favoriteValue("한강가기")
				.build();
		fRepo.save(fmenu2);
		FavoriteMenuVO fmenu3 = FavoriteMenuVO.builder()
				.favoriteValue("고양이")
				.build();
		fRepo.save(fmenu3);
		FavoriteMenuVO fmenu4 = FavoriteMenuVO.builder()
				.favoriteValue("강아지")
				.build();
		fRepo.save(fmenu4);
		FavoriteMenuVO fmenu5 = FavoriteMenuVO.builder()
				.favoriteValue("산")
				.build();
		fRepo.save(fmenu5);
		FavoriteMenuVO fmenu6 = FavoriteMenuVO.builder()
				.favoriteValue("바다")
				.build();
		fRepo.save(fmenu6);
		FavoriteMenuVO fmenu7 = FavoriteMenuVO.builder()
				.favoriteValue("소주")
				.build();
		fRepo.save(fmenu7);
		FavoriteMenuVO fmenu8 = FavoriteMenuVO.builder()
				.favoriteValue("비흡연")
				.build();
		fRepo.save(fmenu8);
		FavoriteMenuVO fmenu9 = FavoriteMenuVO.builder()
				.favoriteValue("흡연")
				.build();
		fRepo.save(fmenu9);
		FavoriteMenuVO fmenu10 = FavoriteMenuVO.builder()
				.favoriteValue("영화")
				.build();
		fRepo.save(fmenu10);
		FavoriteMenuVO fmenu11 = FavoriteMenuVO.builder()
				.favoriteValue("드라마")
				.build();
		fRepo.save(fmenu11);
		FavoriteMenuVO fmenu12 = FavoriteMenuVO.builder()
				.favoriteValue("전화")
				.build();
		fRepo.save(fmenu12);
		FavoriteMenuVO fmenu13 = FavoriteMenuVO.builder()
				.favoriteValue("문자")
				.build();
		fRepo.save(fmenu13);
		FavoriteMenuVO fmenu14 = FavoriteMenuVO.builder()
				.favoriteValue("실내")
				.build();
		fRepo.save(fmenu14);
		FavoriteMenuVO fmenu15 = FavoriteMenuVO.builder()
				.favoriteValue("실외")
				.build();
		fRepo.save(fmenu15);
		FavoriteMenuVO fmenu16 = FavoriteMenuVO.builder()
				.favoriteValue("집순이")
				.build();
		fRepo.save(fmenu16);
	}
	@Test
	public void insert2() {
		IdealMenuVO imenu1 = IdealMenuVO.builder()
				.idealValue("180cm이상")
				.build();
		iRepo.save(imenu1);
		IdealMenuVO imenu2 = IdealMenuVO.builder()
				.idealValue("180cm이하")
				.build();
		iRepo.save(imenu2);
		IdealMenuVO imenu3 = IdealMenuVO.builder()
				.idealValue("170cm이하")
				.build();
		iRepo.save(imenu3);
		IdealMenuVO imenu4 = IdealMenuVO.builder()
				.idealValue("170cm이하")
				.build();
		iRepo.save(imenu4);
		IdealMenuVO imenu5 = IdealMenuVO.builder()
				.idealValue("160cm이상")
				.build();
		iRepo.save(imenu5);
		IdealMenuVO imenu6 = IdealMenuVO.builder()
				.idealValue("160cm이하")
				.build();
		iRepo.save(imenu6);
		IdealMenuVO imenu7 = IdealMenuVO.builder()
				.idealValue("180cm이상")
				.build();
		iRepo.save(imenu7);
		IdealMenuVO imenu8 = IdealMenuVO.builder()
				.idealValue("안경")
				.build();
		iRepo.save(imenu8);
		IdealMenuVO imenu9 = IdealMenuVO.builder()
				.idealValue("유쌍")
				.build();
		iRepo.save(imenu9);
		IdealMenuVO imenu10 = IdealMenuVO.builder()
				.idealValue("무쌍")
				.build();
		iRepo.save(imenu10);
		IdealMenuVO imenu11 = IdealMenuVO.builder()
				.idealValue("곱슬")
				.build();
		iRepo.save(imenu11);
		IdealMenuVO imenu12 = IdealMenuVO.builder()
				.idealValue("장발")
				.build();
		iRepo.save(imenu12);
		IdealMenuVO imenu13 = IdealMenuVO.builder()
				.idealValue("단발")
				.build();
		iRepo.save(imenu13);
		IdealMenuVO imenu14 = IdealMenuVO.builder()
				.idealValue("직모")
				.build();
		iRepo.save(imenu14);
		IdealMenuVO imenu15 = IdealMenuVO.builder()
				.idealValue("고양이상")
				.build();
		iRepo.save(imenu15);
		IdealMenuVO imenu16 = IdealMenuVO.builder()
				.idealValue("강아지상")
				.build();
		iRepo.save(imenu16);
		IdealMenuVO imenu17 = IdealMenuVO.builder()
				.idealValue("수다쟁이")
				.build();
		iRepo.save(imenu17);
		IdealMenuVO imenu18 = IdealMenuVO.builder()
				.idealValue("조용한")
				.build();
		iRepo.save(imenu18);
		IdealMenuVO imenu19 = IdealMenuVO.builder()
				.idealValue("곰상")
				.build();
		iRepo.save(imenu19);
		IdealMenuVO imenu20 = IdealMenuVO.builder()
				.idealValue("토끼상")
				.build();
		iRepo.save(imenu20);
		IdealMenuVO imenu21 = IdealMenuVO.builder()
				.idealValue("공룡상")
				.build();
		iRepo.save(imenu21);
		FavoriteMenuVO fmenu18 = FavoriteMenuVO.builder()
				.favoriteValue("떡볶이")
				.build();
		fRepo.save(fmenu18);
		FavoriteMenuVO fmenu19 = FavoriteMenuVO.builder()
				.favoriteValue("민트초코")
				.build();
		fRepo.save(fmenu19);
		FavoriteMenuVO fmenu20 = FavoriteMenuVO.builder()
				.favoriteValue("치킨")
				.build();
		fRepo.save(fmenu20);
	}
	@Test
	public void insert3() {
		HobbyMenuVO hmenu1 = HobbyMenuVO.builder()
				.hobbyValue("등산")
				.build();
		hRepo.save(hmenu1);
		HobbyMenuVO hmenu2 = HobbyMenuVO.builder()
				.hobbyValue("헬스")
				.build();
		hRepo.save(hmenu2);
		HobbyMenuVO hmenu3 = HobbyMenuVO.builder()
				.hobbyValue("베이킹")
				.build();
		hRepo.save(hmenu3);
		HobbyMenuVO hmenu4 = HobbyMenuVO.builder()
				.hobbyValue("요리")
				.build();
		hRepo.save(hmenu4);
		HobbyMenuVO hmenu5 = HobbyMenuVO.builder()
				.hobbyValue("유튜브")
				.build();
		hRepo.save(hmenu5);
		HobbyMenuVO hmenu6 = HobbyMenuVO.builder()
				.hobbyValue("넷플릭스")
				.build();
		hRepo.save(hmenu6);
		HobbyMenuVO hmenu7 = HobbyMenuVO.builder()
				.hobbyValue("영화")
				.build();
		hRepo.save(hmenu7);
		HobbyMenuVO hmenu8 = HobbyMenuVO.builder()
				.hobbyValue("미드")
				.build();
		hRepo.save(hmenu8);
		HobbyMenuVO hmenu9 = HobbyMenuVO.builder()
				.hobbyValue("k-드라마")
				.build();
		hRepo.save(hmenu9);
		HobbyMenuVO hmenu10 = HobbyMenuVO.builder()
				.hobbyValue("독서")
				.build();
		hRepo.save(hmenu10);
		HobbyMenuVO hmenu11 = HobbyMenuVO.builder()
				.hobbyValue("산책")
				.build();
		hRepo.save(hmenu11);
		HobbyMenuVO hmenu12 = HobbyMenuVO.builder()
				.hobbyValue("사진찍기")
				.build();
		hRepo.save(hmenu12);
		HobbyMenuVO hmenu13 = HobbyMenuVO.builder()
				.hobbyValue("쇼핑")
				.build();
		hRepo.save(hmenu13);
		
	}
}
