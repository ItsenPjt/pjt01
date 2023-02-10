package com.newcen.newcen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
@Slf4j
public class NewcenApplication {


	public static void main(String[] args) {

		double min = 1;
		double max = 4;
		double random = (int) ((Math.random() * (max - min)));

		log.info("점심은 바로  : {} ", random);

		SpringApplication.run(NewcenApplication.class, args);
	}

}
