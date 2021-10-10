package com.itsol.services;

import com.itsol.DTO.GuestDTO;
import com.itsol.DTO.HistoryDTO;
import com.itsol.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    @Autowired
    GuestRepository guestRepository;

    public List<GuestDTO> getAll(int page) {
        return guestRepository.findAll(page);
    }

    public List<HistoryDTO> getHistory(int guestID) {
        return guestRepository.getHistory(guestID);
    }

    public boolean saveOrUpdate(GuestDTO guest) {
        if (guest.getId() == null)
            return guestRepository.createGuest(guest) != null;
        else
            return guestRepository.updateGuest(guest);
    }

    public int getTotalPage() {
        return guestRepository.getTotalPage();
    }
}
