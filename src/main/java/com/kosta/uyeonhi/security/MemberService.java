package com.kosta.uyeonhi.security;

import javax.servlet.http.HttpSession;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.uyeonhi.signUp.UserRepository;
import com.kosta.uyeonhi.signUp.UserVO;

@Service
public class MemberService implements UserDetailsService{
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private UserRepository uRepo;
	@Autowired
	PasswordEncoder passwordEncoder; // security config에서 Bean생성
	// 회원가입
	@Transactional
	public UserVO joinUser(UserVO user) {
		// 비밀번호 암호화...암호화되지않으면 로그인되지않는다.
		String pwd = passwordEncoder.encode(user.getPassword());
		user.setPassword(pwd);
		System.out.println("암호화된 pass:" + pwd);
		return uRepo.save(user);
	}

	//!!!!반드시 구현해야한다. 
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername mid:" + uid);
		//filter는 조건에 맞는것만 선택
		//map: 변형한다. 
	 
		UserDetails user = uRepo.findById(uid)
				.filter(u -> u != null).map(u -> new SecurityUser(u)).get();
		System.out.println("UserDetails member:" + user);
		httpSession.setAttribute("user", uRepo.findById(uid).get());
		return user;
	}

	 
	
}
 