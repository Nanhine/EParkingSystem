package com.eparking.model.repository;

import com.eparking.model.entity.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    Optional<ParkingSlot> findFirstBySlotTypeAndOccupiedFalse(String slotType);
    List<ParkingSlot> findBySlotType(String slotType);
}