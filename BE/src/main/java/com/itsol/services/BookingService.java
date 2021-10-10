package com.itsol.services;

import com.itsol.DTO.*;
import com.itsol.repositories.BookingRepository;
import com.itsol.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    GuestRepository guestRepository;

//    public BookingDTO getBookingInfo(int bookingID) {
//        return bookingRepository.getBookingByBookingID(bookingID);
//    }

    public List<BookingDTO> getBookingList(BookingFilterDTO bookingFilter) {
        return bookingRepository.getBookingList(bookingFilter.getText(), bookingFilter.getDate_from(), bookingFilter.getDate_to());
    }

    public boolean createBooking(BookingCreateDTO bookingCreate) {
        return bookingRepository.createBooking(bookingCreate);
    }

    public boolean deleteBooking(int bookingId) {
        return bookingRepository.deletBooking(bookingId);
    }

    public void saveGuests(RoomingListDTO roomingListDTO) {
        //Nếu idOld không có trong idNew thì xóa
        List<Integer> idListOld = bookingRepository.getIdList(roomingListDTO.getId());
        List<Integer> idListNew = roomingListDTO.getGuestList().stream().map(guest -> guest.getId()).collect(Collectors.toList());
        idListOld.stream().filter(id -> idListNew.indexOf(id) == -1).forEach(id -> {
            bookingRepository.deleteGuestFromRoomBooked(id);
        });
        roomingListDTO.getGuestList().stream().forEach(guest -> {
            Integer guest_id = guest.getId();
            //Nếu id không tồn tại thì thêm mới
            if (guest_id == null) {
                guest_id = guestRepository.createGuest(guest);
                bookingRepository.saveGuestToRoomBooked(guest_id, roomingListDTO.getId());
                //Nếu id có tồn tại nhưng không tồn tại trong idOld thì cập nhật mới
            } else if (idListOld.indexOf(guest_id) == -1) {
                guestRepository.updateGuest(guest);
                bookingRepository.saveGuestToRoomBooked(guest_id, roomingListDTO.getId());
                //Còn lại update thông tin khách
            } else {
                guestRepository.updateGuest(guest);
            }
        });
    }

    public void createRoom(int bookingID, RoomDetail room) {
        bookingRepository.createRoom(bookingID, room);
    }

    public void deleteRoom(int bookedRoomID) {
        bookingRepository.deleteRoom(bookedRoomID);
    }

    public List<GuestDTO> searchGuest(String info) {
        return bookingRepository.searchGuest(info);
    }

    public List<CheckOutDTO> getCheckOut() {
        return bookingRepository.getCheckOut();
    }
}
