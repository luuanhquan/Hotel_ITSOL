package com.itsol.controllers;

import com.itsol.DTO.GuestDTO;
import com.itsol.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guest")
public class GuestController {
    @Autowired
    GuestService guestService;

    @GetMapping("/{page}")
    public ResponseEntity getAll(@PathVariable("page") int page) {
        return new ResponseEntity(guestService.getAll(page), HttpStatus.OK);
    }

    @GetMapping("/total-page")
    public ResponseEntity getTotalPage() {
        return new ResponseEntity(guestService.getTotalPage(), HttpStatus.OK);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity getHistory(@PathVariable("id") int guestID) {
        return new ResponseEntity(guestService.getHistory(guestID), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity createOrUpdate(@RequestBody GuestDTO guestDTO) {
        if (guestService.saveOrUpdate(guestDTO)) {
            return new ResponseEntity(HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
