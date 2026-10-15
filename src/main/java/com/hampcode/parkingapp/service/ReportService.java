package com.hampcode.parkingapp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hampcode.parkingapp.dto.response.StaySummaryDTO;
import com.hampcode.parkingapp.dto.response.VehicleReportResponseDTO;
import com.hampcode.parkingapp.entity.Stay;
import com.hampcode.parkingapp.entity.Vehicle;
import com.hampcode.parkingapp.mapper.ReportMapper;
import com.hampcode.parkingapp.repository.StayRepository;
import com.hampcode.parkingapp.repository.VehicleRepository;

@Service
public class ReportService {
    private final StayRepository stayRepository;
    private final VehicleRepository vehicleRepository;
    private final ReportMapper reportMapper;

    public ReportService(StayRepository stayRepository, VehicleRepository vehicleRepository, ReportMapper reportMapper) {
        this.stayRepository = stayRepository;
        this.vehicleRepository = vehicleRepository;
        this.reportMapper = reportMapper;
    }

    // Generar reporte de un vehiculo
    @Transactional(readOnly = true)
    public VehicleReportResponseDTO generateReport(Long vehicleId) {
        // Info del vehiculo
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));

        // Info estadias del vehiculo
        List<Stay> stays = stayRepository.findAllByVehicleId(vehicleId);

        // Totales en una sola pasada
        long totalStays = stays.size(); // -> total de estadias
        long totalHours = 0;
        BigDecimal totalCharged = BigDecimal.ZERO;
        BigDecimal highestCharge = BigDecimal.ZERO;

        for (Stay stay : stays) {
            totalHours += stay.getHours(); // -> total de horas
            totalCharged = totalCharged.add(stay.getCharge()); // -> total cobrado
            if (stay.getCharge().compareTo(highestCharge) > 0) { // -> cobro mas alto
                highestCharge = stay.getCharge();
            }
        }

        // Promedio charges (evitar division por cero)
        BigDecimal averageCharge = totalStays == 0
                ? BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP) // -> 0
                : totalCharged.divide(BigDecimal.valueOf(totalStays), 2, RoundingMode.HALF_UP); // -> promedio

        // Listado de estadias [lotName, hours, charge] -> StaySummaryDTO
        List<Object[]> summaryRows = stayRepository.findStaySummariesByVehicleId(vehicleId);
        List<StaySummaryDTO> staySummaries = reportMapper.toStaySummaries(summaryRows);

        // Devolver reporte
        return reportMapper.toVehicleReport(vehicle, totalStays, totalHours, totalCharged,
                                            averageCharge, highestCharge, staySummaries);
    }
}
