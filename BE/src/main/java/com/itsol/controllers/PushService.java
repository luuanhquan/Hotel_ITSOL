package com.itsol.controllers;

import com.itsol.services.BookingService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Data
@Service
public class PushService {
    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/topic/greetings";
    private final SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    BookingService bookingService;

    PushService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendMess() {
        ;
        simpMessagingTemplate.convertAndSend(WS_MESSAGE_TRANSFER_DESTINATION, bookingService.getCheckOut());
    }
}
