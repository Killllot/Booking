package com.newBooking.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories("com.newBooking")
@EntityScan("com.newBooking.domain")
@ComponentScan("com.newBooking")
@PropertySource("classpath:mySecretProperties.properties")
@SpringBootApplication
public class BookingApp {
    public static void main(String[] args) {
        SpringApplication.run(BookingApp.class, args);
    }
}
