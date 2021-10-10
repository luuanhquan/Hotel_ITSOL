package com.itsol.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Available {
    int rtID;
    String roomType;
    int rate;
    List<RoomDetail> roomList;

    public Available(int rtID, String roomType, int rate) {
        this.rtID = rtID;
        this.roomType = roomType;
        this.rate = rate;
    }
}
