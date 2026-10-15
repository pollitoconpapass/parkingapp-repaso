package com.hampcode.parkingapp.dto.response;

import java.math.BigDecimal;

public record StaySummaryDTO(
    String lotName, 
    int hours, 
    BigDecimal charge
) {}
