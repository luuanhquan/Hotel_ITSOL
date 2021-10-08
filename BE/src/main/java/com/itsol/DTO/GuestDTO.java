package com.itsol.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestDTO{
    Integer id;
    String name;
    String gender;
    String address;
    String phone;
    String personal_id;
}
