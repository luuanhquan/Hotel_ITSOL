package com.itsol.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomingListDTO {
    Integer id;
    int room_number;
    int rate;
    String roomType;
    List<GuestDTO> guestList;

    public RoomingListDTO(int id, int room_number, int rate, String roomType) {
        this.id = id;
        this.room_number = room_number;
        this.rate = rate;
        this.roomType = roomType;
    }
}
