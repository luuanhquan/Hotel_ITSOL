package com.itsol.services;

import com.itsol.DTO.*;
import com.itsol.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

//    public BookingDTO getBookingInfo(int bookingID) {
//        return bookingRepository.getBookingByBookingID(bookingID);
//    }

    public List<BookingDTO> getBookingList(BookingFilterDTO bookingFilter) {
        return bookingRepository.getBookingList(bookingFilter.getText(), bookingFilter.getDate_from(),bookingFilter.getDate_to());
    }

    public boolean createBooking(BookingCreateDTO bookingCreate) {
        return bookingRepository.createBooking(bookingCreate);
    }

    public boolean deleteBooking(int bookingId) {
        return bookingRepository.deletBooking(bookingId);
    }

    public void saveGuests(RoomingListDTO roomingListDTO) {
        bookingRepository.updateOrSaveGuestList(roomingListDTO);
    }

    public void createRoom(int bookingID, RoomDetail room) {
       bookingRepository.createRoom(bookingID,room);
    }

    public void deleteRoom(int bookedRoomID) {
        bookingRepository.deleteRoom(bookedRoomID);
    }
}
