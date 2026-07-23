package com.eparking.model.dto;

public class BookSlotRequest {
    private Long userId;
    private String vehicleNumber;
    private String vehicleType;
    private Long slotId;
    private Integer plannedMinutes;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public Long getSlotId() { return slotId; }
    public void setSlotId(Long slotId) { this.slotId = slotId; }
    public Integer getPlannedMinutes() { return plannedMinutes; }
    public void setPlannedMinutes(Integer plannedMinutes) { this.plannedMinutes = plannedMinutes; }
}