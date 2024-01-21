package com.example.apiProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

@SpringBootApplication
public class ApiProject {

    public static void main(String[] args) {
        SpringApplication.run(ApiProject.class, args);
    }

}
