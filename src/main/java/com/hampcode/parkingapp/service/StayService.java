package com.hampcode.parkingapp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.hampcode.parkingapp.dto.request.StayRequestDTO;
import com.hampcode.parkingapp.dto.response.StayResponseDTO;
import com.hampcode.parkingapp.repository.ParkingLotRepository;
import com.hampcode.parkingapp.repository.StayRepository;
import com.hampcode.parkingapp.repository.VehicleRepository;

import com.hampcode.parkingapp.entity.ParkingLot;
import com.hampcode.parkingapp.entity.Stay;
import com.hampcode.parkingapp.entity.Vehicle;
import com.hampcode.parkingapp.entity.VehicleType;
import com.hampcode.parkingapp.exception.NoSpacesAvailableException;
import com.hampcode.parkingapp.mapper.StayMapper;

import org.springframework.transaction.annotation.Transactional;

@Service
public class StayService {
    private static final double RECARGO_CAMIONETA = 1.20;
    private static final double DESCUENTO_SOCIO = 0.90;
    private static final double MINIMUM_CHARGE = 5.0;
    private static final double HOURLY_RATE = 5.0;


    private final StayRepository stayRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final StayMapper stayMapper;

    public StayService(StayRepository stayRepository, VehicleRepository vehicleRepository, ParkingLotRepository parkingLotRepository, StayMapper stayMapper) {
        this.stayRepository = stayRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.stayMapper = stayMapper;
    }

    @Transactional
    public StayResponseDTO registrarVehiculo(StayRequestDTO request) {
        // Validar si el vehiculo existe
        Vehicle vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));

        // Validar si el estacionamiento existe
        ParkingLot lot = parkingLotRepository.findById(request.lotId())
                .orElseThrow(() -> new RuntimeException("Estacionamiento no encontrado"));

        // Validar si el lote aun tiene espacio
        if (lot.getAvailableSpaces() == 0) {
            throw new NoSpacesAvailableException("La playa no tiene espacios libres");
        }

        // Verificar que hora este entre 1 y 24
        if (request.hours() < 1 || request.hours() > 24) {
            throw new RuntimeException("Las horas deben estar entre 1 y 24");
        }

        // Calcular precio
        double precio = calcularPrecio(request.hours(), vehicle.getType(), vehicle.getMember());
        BigDecimal charge = BigDecimal.valueOf(precio).setScale(2, RoundingMode.HALF_UP);

        // Guardar estadia
        Stay stay = Stay.builder()
                .vehicle(vehicle)
                .lot(lot)
                .hours(request.hours())
                .charge(charge)
                .build();
        stay = stayRepository.save(stay);

        // Descontar espacio ocupado
        lot.setAvailableSpaces(lot.getAvailableSpaces() - 1);
        parkingLotRepository.save(lot);

        return stayMapper.toResponse(stay);
    }

    // Helper function (calcular el charge)
    private double calcularPrecio(int horas, VehicleType tipoVehiculo, boolean socio){
        double finalCharge;

        if (tipoVehiculo == VehicleType.CAMIONETA){
            finalCharge = horas * HOURLY_RATE * RECARGO_CAMIONETA;
        } else {
            finalCharge = horas * HOURLY_RATE;
        }

        if (socio){
            finalCharge = finalCharge * DESCUENTO_SOCIO;
        }

        return finalCharge > MINIMUM_CHARGE ? finalCharge : MINIMUM_CHARGE;

    }


}
