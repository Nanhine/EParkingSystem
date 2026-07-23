package com.eparking.model.controller;

import com.eparking.model.entity.ParkingSlot;
import com.eparking.model.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin(origins = "*")
public class SlotController {

    @Autowired
    private ParkingSlotRepository slotRepo;

    @GetMapping
    public List<ParkingSlot> getSlots(@RequestParam(required = false) String type) {
        if (type != null && !type.isBlank()) {
            return slotRepo.findBySlotType(type.toUpperCase());
        }
        return slotRepo.findAll();
    }
}