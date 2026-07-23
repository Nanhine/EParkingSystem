package com.eparking.model.config;

import com.eparking.model.entity.ParkingSlot;
import com.eparking.model.repository.ParkingSlotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ParkingSlotRepository slotRepo;

    public DataSeeder(ParkingSlotRepository slotRepo) {
        this.slotRepo = slotRepo;
    }

    @Override
    public void run(String... args) {
        if (slotRepo.count() == 0) {
            String[][] slots = {
                {"A1", "CAR"}, {"A2", "CAR"}, {"A3", "CAR"},
                {"B1", "BIKE"}, {"B2", "BIKE"},
                {"T1", "TRUCK"}
            };
            for (String[] s : slots) {
                ParkingSlot slot = new ParkingSlot();
                slot.setSlotNumber(s[0]);
                slot.setSlotType(s[1]);
                slot.setOccupied(false);
                slotRepo.save(slot);
            }
            System.out.println("Seeded " + slots.length + " parking slots.");
        }
    }
}