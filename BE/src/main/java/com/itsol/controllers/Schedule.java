package com.itsol.controllers;

import com.itsol.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Schedule {
    private final PushService greetingService;
    @Autowired
    BookingService bookingService;

    Schedule(PushService greetingService) {
        this.greetingService = greetingService;
    }


    @Scheduled(cron = "*/20 * * * * *")
    public void send() {
        greetingService.sendMess();
    }
}
