package com.kosta.uyeonhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@ComponentScan("com.kosta")
@EntityScan("com.kosta")
@EnableJpaAuditing
@SpringBootApplication
public class UyeonhiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UyeonhiApplication.class, args);
	}

}
