package com.hampcode.parkingapp.dto.request;

public record StayRequestDTO(
    Long vehicleId,
    Long lotId,
    int hours
) {}
