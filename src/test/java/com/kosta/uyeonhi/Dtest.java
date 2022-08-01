package com.kosta.uyeonhi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.uyeonhi.signUp.UserRepository;

@SpringBootTest
public class Dtest {
	@Autowired
	UserRepository uRepo;
	@Test
	public void test() {
		uRepo.deleteById("hyj1077");
	}
}
