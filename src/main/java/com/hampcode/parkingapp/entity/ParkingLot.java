package com.hampcode.parkingapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity 
@Table(name = "parking_lots") 
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // tarifa por hora, en soles
    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    // espacios que todavia estan libres en la playa
    @Column(name = "available_spaces", nullable = false)
    private Integer availableSpaces;
}
