package com.itsol.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {
    int booking_id;
    Date date_from;
    Date date_to;
    int room_number;
    String room_type;
    int rate;
}
