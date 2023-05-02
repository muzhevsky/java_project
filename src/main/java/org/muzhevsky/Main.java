package org.muzhevsky;


import org.muzhevsky.authorization.enums.Role;
import org.muzhevsky.authorization.services.JwtTokenService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        var ctx = new AnnotationConfigApplicationContext("org.muzhevsky");
    }
}