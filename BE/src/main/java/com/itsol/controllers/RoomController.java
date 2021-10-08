package com.itsol.controllers;

import com.itsol.DTO.BookingFilterDTO;
import com.itsol.DTO.RoomDTO;
import com.itsol.DTO.RoomTypeDTO;
import com.itsol.services.RoomService;
import com.itsol.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomService roomService;
    @Autowired
    RoomTypeService roomTypeService;

    @PostMapping("/available")
    public ResponseEntity getAvailableRooms(@RequestBody() BookingFilterDTO filter) {
        return new ResponseEntity(roomTypeService.getAvailableRooms(filter), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity getAllRoom() {
        return new ResponseEntity(roomService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody() RoomDTO room) {
        if (roomService.create(room))
            return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody() RoomDTO room) {
        if (roomService.update(room))
            return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRoom(@PathVariable("id") int id) {
        if (roomService.deleteRoom(id))
            return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/type")
    public ResponseEntity getAllType() {
        return new ResponseEntity(roomTypeService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/type/update")
    public ResponseEntity updateOrSaveRoomType(@RequestBody List<RoomTypeDTO> roomTypeList) {
        try {
            roomTypeService.saveOrUpdate(roomTypeList);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
