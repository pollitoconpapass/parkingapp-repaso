package com.hampcode.parkingapp.dto.response;

import java.math.BigDecimal;

public record StayResponseDTO(
    Long id, 
    String plate, 
    String lotName, 
    int hours, 
    BigDecimal charge) {        
}
