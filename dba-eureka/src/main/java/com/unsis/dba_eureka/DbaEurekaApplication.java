package com.unsis.dba_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
@EnableEurekaServer
@SpringBootApplication
public class DbaEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbaEurekaApplication.class, args);
	}

}
