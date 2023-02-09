package com.newcen.newcen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
public class NewcenApplication {


	public static void main(String[] args) {
		SpringApplication.run(NewcenApplication.class, args);
	}

}
