package com.itsol.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreateDTO {
    String booker;
    String notice;
    Date date_from;
    Date date_to;
    List<Integer> roomList;
}
