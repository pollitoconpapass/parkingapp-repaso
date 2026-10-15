package com.hampcode.parkingapp.repository;

import com.hampcode.parkingapp.entity.Stay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StayRepository extends JpaRepository<Stay, Long> {
    // Consultar todas las estadías de un vehiculo
    @Query("SELECT s FROM Stay s WHERE s.vehicle.id = :vehicleId")
    public List<Stay> findAllByVehicleId(@Param("vehicleId") Long vehicleId);

    // Filas [lotName, hours, charge] para el resumen del reporte
    @Query("SELECT s.lot.name, s.hours, s.charge FROM Stay s WHERE s.vehicle.id = :vehicleId")
    public List<Object[]> findStaySummariesByVehicleId(@Param("vehicleId") Long vehicleId);

    // Contar total de estadias
    @Query("SELECT COUNT(s) FROM Stay s WHERE s.vehicle.id = :vehicleId")
    public Long countByVehicleId(@Param("vehicleId") Long vehicleId);

}
