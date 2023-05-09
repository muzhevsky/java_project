package org.muzhevsky;


import org.muzhevsky.authorization.enums.Role;
import org.muzhevsky.authorization.services.JwtTokenService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@SpringBootApplication(scanBasePackages = "org.muzhevsky")
public class Main {
    public static void main(String[] args) throws IOException {
       var ctx = SpringApplication.run(Main.class, args);
    }
}