package com.itsol.services;

import com.itsol.DTO.RoomDTO;
import com.itsol.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    public List<RoomDTO> getAll() {
        return roomRepository.getAll();
    }

//    public boolean saveOrUpdate(RoomDTO room) {
//        return roomRepository.saveOrUpdate(room);
//    }

    public boolean deleteRoom(int id) {
        return roomRepository.deleteRoom(id);
    }

    public boolean create(RoomDTO room) {
        return roomRepository.saveRoom(room);
    }

    public boolean update(RoomDTO room) {
        return roomRepository.updateRoom(room);
    }
}
