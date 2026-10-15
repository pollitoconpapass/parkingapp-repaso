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
    // TODO
}
