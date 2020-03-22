package com.ksu.catcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ApplicationProperties.class})
@SpringBootApplication
public class CatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatcherApplication.class, args);

	
	
	
}
	
}
