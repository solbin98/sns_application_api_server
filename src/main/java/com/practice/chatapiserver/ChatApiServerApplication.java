package com.practice.chatapiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class ChatApiServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApiServerApplication.class, args);
    }

}
