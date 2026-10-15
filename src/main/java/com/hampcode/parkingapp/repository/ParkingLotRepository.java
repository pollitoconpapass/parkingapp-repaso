package com.hampcode.parkingapp.repository;

import com.hampcode.parkingapp.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}
