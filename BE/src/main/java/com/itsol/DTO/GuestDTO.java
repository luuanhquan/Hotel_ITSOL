package com.itsol.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestDTO {
    Integer id;
    String name;
    Boolean gender;
    String address;
    String phone;
    String personal_id;
}
