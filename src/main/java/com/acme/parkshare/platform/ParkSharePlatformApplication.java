package com.acme.parkshare.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ParkSharePlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParkSharePlatformApplication.class, args);
    }
}
