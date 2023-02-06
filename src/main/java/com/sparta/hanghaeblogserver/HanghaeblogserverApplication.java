package com.sparta.hanghaeblogserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HanghaeblogserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeblogserverApplication.class, args);
    }

}
