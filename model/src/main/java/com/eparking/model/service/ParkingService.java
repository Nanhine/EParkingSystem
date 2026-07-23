package com.eparking.model.service;

import com.eparking.model.dto.BookSlotRequest;
import com.eparking.model.dto.BookingResponse;
import com.eparking.model.entity.*;
import com.eparking.model.repository.*;
import com.eparking.model.util.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkingService {

    @Autowired private UserRepository userRepo;
    @Autowired private VehicleRepository vehicleRepo;
    @Autowired private ParkingSlotRepository slotRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private EmailService emailService;

    @Value("${billing.rate.per.hour}")
    private double ratePerHour;

    @Transactional
    public synchronized BookingResponse bookSlot(BookSlotRequest req) {
        ParkingSlot slot = slotRepo.findById(req.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.isOccupied()) {
            throw new IllegalStateException("That slot was just taken. Please pick another.");
        }
        if (!slot.getSlotType().equalsIgnoreCase(req.getVehicleType())) {
            throw new IllegalStateException("This slot is for " + slot.getSlotType() + " only.");
        }

        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found - please log in again."));

        Vehicle vehicle = vehicleRepo.findByUserUserIdAndVehicleNumber(req.getUserId(), req.getVehicleNumber())
                .orElseGet(() -> {
                    Vehicle v = new Vehicle();
                    v.setUser(user);
                    v.setVehicleNumber(req.getVehicleNumber());
                    v.setVehicleType(req.getVehicleType().toUpperCase());
                    return vehicleRepo.save(v);
                });

        LocalDateTime entryTime = LocalDateTime.now();

        Booking booking = new Booking();
        booking.setVehicle(vehicle);
        booking.setSlot(slot);
        booking.setEntryTime(entryTime);
        booking.setStatus("ACTIVE");
        booking.setPlannedMinutes(req.getPlannedMinutes());
        booking = bookingRepo.save(booking);

        slot.setOccupied(true);
        slotRepo.save(slot);

        String html = EmailTemplate.confirmationEmail(
                user.getName(), vehicle.getVehicleNumber(), slot.getSlotNumber(), entryTime, req.getPlannedMinutes());
        emailService.sendHtmlEmail(user.getEmail(), "Parking Confirmed - Slot " + slot.getSlotNumber(), html);

        return new BookingResponse(booking.getBookingId(), "Slot booked", 0.0,
                vehicle.getVehicleNumber(), slot.getSlotNumber(), entryTime, null, ratePerHour, req.getPlannedMinutes());
    }

    @Transactional
    public BookingResponse checkOut(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if ("COMPLETED".equals(booking.getStatus())) {
            throw new IllegalStateException("Already checked out.");
        }

        LocalDateTime exitTime = LocalDateTime.now();
        long minutes = Duration.between(booking.getEntryTime(), exitTime).toMinutes();
        long hoursCharged = (long) Math.ceil(minutes / 60.0);
        if (hoursCharged == 0) hoursCharged = 1;
        double amount = hoursCharged * ratePerHour;

        booking.setExitTime(exitTime);
        booking.setAmount(amount);
        booking.setStatus("COMPLETED");
        bookingRepo.save(booking);

        ParkingSlot slot = booking.getSlot();
        slot.setOccupied(false);
        slotRepo.save(slot);

        Vehicle vehicle = booking.getVehicle();
        User owner = vehicle.getUser();
        String html = EmailTemplate.billEmail(
                owner.getName(), vehicle.getVehicleNumber(), slot.getSlotNumber(),
                booking.getEntryTime(), exitTime, minutes, ratePerHour, amount);
        emailService.sendHtmlEmail(owner.getEmail(), "Parking Bill - " + vehicle.getVehicleNumber(), html);

        return new BookingResponse(bookingId, "Checked out", amount,
                vehicle.getVehicleNumber(), slot.getSlotNumber(),
                booking.getEntryTime(), exitTime, ratePerHour, booking.getPlannedMinutes());
    }

    public Optional<BookingResponse> getActiveBooking(Long userId) {
        return bookingRepo.findFirstByVehicle_User_UserIdAndStatusOrderByEntryTimeDesc(userId, "ACTIVE")
                .map(b -> new BookingResponse(b.getBookingId(), "Active", 0.0,
                        b.getVehicle().getVehicleNumber(), b.getSlot().getSlotNumber(),
                        b.getEntryTime(), null, ratePerHour, b.getPlannedMinutes()));
    }
}