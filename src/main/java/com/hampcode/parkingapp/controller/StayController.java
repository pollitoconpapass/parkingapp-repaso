package com.hampcode.parkingapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hampcode.parkingapp.dto.request.StayRequestDTO;
import com.hampcode.parkingapp.dto.response.StayResponseDTO;
import com.hampcode.parkingapp.service.StayService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/stays")
public class StayController {
    private final StayService stayService;

    public StayController(StayService stayService){
        this.stayService = stayService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StayResponseDTO registrarEstadia(@Valid @RequestBody StayRequestDTO request) {        
        return stayService.registrarVehiculo(request);
    }
    
}
