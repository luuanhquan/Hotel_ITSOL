package com.itsol.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    int id;
    String booker;
    int room_count;
    Date date_from;
    Date date_to;
    String notice;

    public BookingDTO(int id, String booker, int room_count, Date date_from, Date date_to, String notice) {
        this.id = id;
        this.booker = booker;
        this.room_count = room_count;
        this.date_from = date_from;
        this.date_to = date_to;
        this.notice = notice;
    }

    List<RoomingListDTO> roomingList;
}
