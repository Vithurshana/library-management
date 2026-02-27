package com.example.library.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//enables automatic auditing fields
@EnableJpaAuditing
@EnableAsync
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

//When running:
//Spring Boot starts
//Embedded Tomcat server starts
//Beans are created
//Database connection is established
//Application becomes available at: http://localhost:8080

//Internally when running:
//JVM starts
//SpringApplication.run()
//Auto-configuration begins
//Component scanning starts
//Beans are created
//Embedded Tomcat starts
//Security filter chain loads
//App ready

//Tomcat
//Listens for HTTP requests
//Passes them to your Spring controllers
//Sends HTTP responses back to the client

//spring-boot-starter-web
//Tomcat dependency
//JSON converter
//DispatcherServlet
//MVC configuration