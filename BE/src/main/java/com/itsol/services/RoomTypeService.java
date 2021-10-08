package com.itsol.services;

import com.itsol.DTO.Available;
import com.itsol.DTO.BookingFilterDTO;
import com.itsol.DTO.RoomTypeDTO;
import com.itsol.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService {
    @Autowired
    RoomTypeRepository roomTypeRepository;

    public List<Available> getAvailableRooms(BookingFilterDTO filter) {
        return roomTypeRepository.getAvailable(filter);
    }

    public List<RoomTypeDTO> getAll() {
        return roomTypeRepository.getAll();
    }

    public void saveOrUpdate(List<RoomTypeDTO> roomType){
        this.roomTypeRepository.saveOrUpdate(roomType);
    }
}
