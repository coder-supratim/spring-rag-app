package com.timsoft.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class SpringRagAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRagAppApplication.class, args);
	}

}
