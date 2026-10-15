package com.hampcode.parkingapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hampcode.parkingapp.dto.response.VehicleReportResponseDTO;
import com.hampcode.parkingapp.service.ReportService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/vehicles/{id}/summary")
    public VehicleReportResponseDTO getVehicleReport(@PathVariable Long id) {
        return reportService.generateReport(id);
    }
    
}
