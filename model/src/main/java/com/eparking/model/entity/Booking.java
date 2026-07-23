package com.eparking.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private ParkingSlot slot;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String status;
    private double amount;

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public ParkingSlot getSlot() { return slot; }
    public void setSlot(ParkingSlot slot) { this.slot = slot; }

    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }

    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    private Integer plannedMinutes; // how long the user said they'd park

    public Integer getPlannedMinutes() { return plannedMinutes; }
    public void setPlannedMinutes(Integer plannedMinutes) { this.plannedMinutes = plannedMinutes; }
}