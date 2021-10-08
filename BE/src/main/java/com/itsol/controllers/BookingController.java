package com.itsol.controllers;

import com.itsol.DTO.*;
import com.itsol.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping()
    public ResponseEntity getBookingList(@RequestBody()BookingFilterDTO bookingFilter){
        return new ResponseEntity(bookingService.getBookingList(bookingFilter), HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity createBooking(@RequestBody()BookingCreateDTO bookingCreate){
        System.out.println(bookingCreate);
        bookingService.createBooking(bookingCreate);
        return ResponseEntity.ok("");
//        else
//            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBooking(@PathVariable("id")int bookingId){
        if(bookingService.deleteBooking(bookingId))
        return ResponseEntity.ok("Success!");
        else
            return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/guest/save")
    public ResponseEntity saveGuests(@RequestBody()RoomingListDTO roomingListDTO){
        bookingService.saveGuests(roomingListDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/{id}/room/create")
    public ResponseEntity createRoom(@RequestBody()RoomDetail room, @PathVariable("id")int bookingID){
        System.out.println("OK?");
        bookingService.createRoom(bookingID, room);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/room/delete/{id}")
    public ResponseEntity deleteRoom(@PathVariable("id")int bookedRoomID){
        bookingService.deleteRoom(bookedRoomID);
        return new ResponseEntity(HttpStatus.OK);
    }
}
