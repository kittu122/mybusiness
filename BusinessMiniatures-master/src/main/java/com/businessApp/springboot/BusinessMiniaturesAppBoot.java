package com.businessApp.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"com.businessApp"})
@EnableMongoRepositories(basePackages= {"com.businessApp.repositories"})
public class BusinessMiniaturesAppBoot extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BusinessMiniaturesAppBoot.class);
    }
	
	public static void main(String[] args) 
    {
		System.out.println("In spring boot");
        SpringApplication.run(BusinessMiniaturesAppBoot.class, args);
        
    }
}