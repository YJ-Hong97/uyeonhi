package com.kosta.uyeonhi.security;

import java.util.ArrayList;


import java.util.Collection;
import java.util.List;

import org.aspectj.weaver.NameMangler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.kosta.uyeonhi.signUp.ManagerRepository;
import com.kosta.uyeonhi.signUp.UserVO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class SecurityUser extends User{
	private static final long serialVersionUID = 1L;
	private static final String ROLE_PREFIX="ROLE_";
    private UserVO user;   
    @Autowired
    private static ManagerRepository mRepo;
    
	public SecurityUser(String name, String password, Collection<? extends GrantedAuthority> authorities) {
		super(name, password, authorities);
	}
	public SecurityUser(UserVO user) {	
		super(user.getId(), user.getPassword(), makeRole(user)  );
		this.user = user;
		System.out.println("SecurityUser member:" + user);
	}
	//Role을 여러개 가질수 있도록 되어있음 
	private static List<GrantedAuthority> makeRole(UserVO user) {
		List<GrantedAuthority> roleList = new ArrayList<>();
		//매니저 아이디와 사용자 아이디 중복 불가. 아이디만 검사하면 매니저인지 아닌지 판별가능
		boolean manager = mRepo.existsById(user.getId());
		if(manager) {
			roleList.add(new SimpleGrantedAuthority(ROLE_PREFIX + "manager"));
		}else {
			roleList.add(new SimpleGrantedAuthority(ROLE_PREFIX + "user"));
		}
		
		return roleList;
	}
	 
	//User class에서 username필드가 있지만 google 인증시 사용되는 필드는 name
	//이를 맞추기위해 함수추가함 
	public String getName() {
		// TODO Auto-generated method stub
		return super.getUsername();
	}
	
	
	
	
	
}
