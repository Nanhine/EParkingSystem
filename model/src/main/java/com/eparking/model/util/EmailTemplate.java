package com.eparking.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailTemplate {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    public static String confirmationEmail(String ownerName, String vehicleNumber,
                                            String slotNumber, LocalDateTime entryTime, Integer plannedMinutes) {
        String durationRow = plannedMinutes != null
                ? row("Requested Duration", formatMinutes(plannedMinutes))
                : "";
        return "<div style='font-family:Segoe UI,Arial,sans-serif;max-width:500px;margin:auto;" +
                "border:1px solid #e0e0e0;border-radius:10px;overflow:hidden;'>" +
                "<div style='background:#1a73e8;color:#ffffff;padding:20px;text-align:center;'>" +
                "<h2 style='margin:0;'>&#9989; Parking Confirmed</h2></div>" +
                "<div style='padding:24px;background:#ffffff;'>" +
                "<p style='font-size:15px;color:#333;'>Hi " + ownerName + ",</p>" +
                "<p style='font-size:14px;color:#555;'>Your vehicle has been checked in:</p>" +
                "<table style='width:100%;border-collapse:collapse;margin-top:12px;'>" +
                row("Vehicle Number", vehicleNumber) +
                row("Slot", slotNumber) +
                row("Entry Time", entryTime.format(FMT)) +
                durationRow +
                "</table></div>" +
                "<div style='background:#f5f5f5;padding:12px;text-align:center;font-size:12px;color:#999;'>" +
                "E-Parking Solutions</div></div>";
    }

    public static String billEmail(String ownerName, String vehicleNumber, String slotNumber,
                                    LocalDateTime entryTime, LocalDateTime exitTime,
                                    long durationMinutes, double ratePerHour, double totalAmount) {
        long hoursCharged = (long) Math.ceil(durationMinutes / 60.0);
        return "<div style='font-family:Segoe UI,Arial,sans-serif;max-width:500px;margin:auto;" +
                "border:1px solid #e0e0e0;border-radius:10px;overflow:hidden;'>" +
                "<div style='background:#188038;color:#ffffff;padding:20px;text-align:center;'>" +
                "<h2 style='margin:0;'>&#129534; Parking Bill</h2></div>" +
                "<div style='padding:24px;background:#ffffff;'>" +
                "<p style='font-size:15px;color:#333;'>Hi " + ownerName + ",</p>" +
                "<table style='width:100%;border-collapse:collapse;margin-top:12px;'>" +
                row("Vehicle Number", vehicleNumber) +
                row("Slot", slotNumber) +
                row("Entry Time", entryTime.format(FMT)) +
                row("Exit Time", exitTime.format(FMT)) +
                row("Duration", durationMinutes + " mins (" + hoursCharged + " hr billed)") +
                row("Rate / Hour", "₹" + String.format("%.2f", ratePerHour)) +
                "<tr style='background:#e6f4ea;'>" +
                "<td style='padding:12px;font-weight:bold;color:#188038;border-top:2px solid #188038;'>Total Amount</td>" +
                "<td style='padding:12px;font-weight:bold;color:#188038;border-top:2px solid #188038;text-align:right;font-size:16px;'>" +
                "₹" + String.format("%.2f", totalAmount) + "</td></tr></table></div>" +
                "<div style='background:#f5f5f5;padding:12px;text-align:center;font-size:12px;color:#999;'>" +
                "E-Parking Solutions</div></div>";
    }

    private static String row(String label, String value) {
        return "<tr><td style='padding:10px;border-bottom:1px solid #eee;color:#666;'>" + label + "</td>" +
                "<td style='padding:10px;border-bottom:1px solid #eee;color:#222;text-align:right;font-weight:600;'>" +
                value + "</td></tr>";
    }

    private static String formatMinutes(int mins) {
        if (mins < 60) return mins + " min";
        return (mins / 60) + " hr " + (mins % 60 == 0 ? "" : (mins % 60) + " min");
    }
}