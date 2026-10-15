package com.hampcode.parkingapp.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record VehicleReportResponseDTO(
    String plate,
    int totalStays,
    int totalHours,
    BigDecimal totalCharged,
    BigDecimal averageCharge,
    BigDecimal highestCharge,
    List <StaySummaryDTO> stays
) {}
