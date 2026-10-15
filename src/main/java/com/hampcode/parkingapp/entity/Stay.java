package com.hampcode.parkingapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "stays")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id", nullable = false)
    private ParkingLot lot;

    // horas que estuvo el vehiculo
    @Column(nullable = false)
    private Integer hours;

    // cobro de la estadia, en soles
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal charge;
}
