package com.eparking.model.repository;

import com.eparking.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findFirstByVehicle_User_UserIdAndStatusOrderByEntryTimeDesc(Long userId, String status);
}