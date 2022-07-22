package com.kosta.uyeonhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan("com.kosta")
@EntityScan("com.kosta")
@SpringBootApplication
public class UyeonhiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UyeonhiApplication.class, args);
	}

}
