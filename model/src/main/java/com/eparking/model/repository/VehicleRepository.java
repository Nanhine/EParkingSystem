package com.eparking.model.repository;

import com.eparking.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByUserUserIdAndVehicleNumber(Long userId, String vehicleNumber);
}