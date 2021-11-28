package com.bol.mancalagame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MancalaGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(MancalaGameApplication.class, args);
	}

}
