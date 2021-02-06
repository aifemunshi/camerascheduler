package com.javainuse.taskconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
@ComponentScan("com.javainuse.taskconfig.*")
@EnableAutoConfiguration
//@EntityScan(basePackages = "com.javainuse.taskconfig")
public class SpringBootMainApplication {

	public static void main(String[] args) {

		SpringApplication.run(new Object[] { SpringBootMainApplication.class }, args);
	}
}