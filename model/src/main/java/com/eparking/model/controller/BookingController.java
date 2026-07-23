package com.eparking.model.controller;

import com.eparking.model.dto.BookSlotRequest;
import com.eparking.model.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/book")
    public ResponseEntity<?> book(@RequestBody BookSlotRequest req) {
        try {
            return ResponseEntity.ok(parkingService.bookSlot(req));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/checkout/{bookingId}")
    public ResponseEntity<?> checkout(@PathVariable Long bookingId) {
        try {
            return ResponseEntity.ok(parkingService.checkOut(bookingId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/active/{userId}")
    public ResponseEntity<?> getActive(@PathVariable Long userId) {
        return parkingService.getActiveBooking(userId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(Map.of("active", false)));
    }
}