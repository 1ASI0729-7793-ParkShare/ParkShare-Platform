package com.acme.parkshare.platform.booking.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;

@Configuration
public class BookingTimeConfiguration {
    @Bean
    @Primary
    public Clock clock() {
        return Clock.systemUTC();
    }
}
