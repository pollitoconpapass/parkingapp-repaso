package com.hampcode.parkingapp.repository;

import com.hampcode.parkingapp.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
