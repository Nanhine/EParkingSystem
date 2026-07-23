package com.eparking.model.dto;

import java.time.LocalDateTime;

public class BookingResponse {
    private Long bookingId;
    private String message;
    private double amount;
    private String vehicleNumber;
    private String slotNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double ratePerHour;
    private Integer plannedMinutes;

    public BookingResponse(Long bookingId, String message, double amount,
                            String vehicleNumber, String slotNumber,
                            LocalDateTime entryTime, LocalDateTime exitTime,
                            double ratePerHour, Integer plannedMinutes) {
        this.bookingId = bookingId;
        this.message = message;
        this.amount = amount;
        this.vehicleNumber = vehicleNumber;
        this.slotNumber = slotNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.ratePerHour = ratePerHour;
        this.plannedMinutes = plannedMinutes;
    }

    public Long getBookingId() { return bookingId; }
    public String getMessage() { return message; }
    public double getAmount() { return amount; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getSlotNumber() { return slotNumber; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public double getRatePerHour() { return ratePerHour; }
    public Integer getPlannedMinutes() { return plannedMinutes; }
}