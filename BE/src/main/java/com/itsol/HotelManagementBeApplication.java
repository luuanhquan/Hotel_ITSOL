package com.itsol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@CrossOrigin
public class HotelManagementBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelManagementBeApplication.class, args);
    }

}
