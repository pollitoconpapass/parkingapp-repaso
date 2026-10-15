package com.hampcode.parkingapp.mapper;

import com.hampcode.parkingapp.dto.response.StaySummaryDTO;
import com.hampcode.parkingapp.dto.response.VehicleReportResponseDTO;
import com.hampcode.parkingapp.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    // cada fila de la consulta: [lotName, hours, charge]
    default StaySummaryDTO toStaySummary(Object[] row) {
        return new StaySummaryDTO((String) row[0], (Integer) row[1], (BigDecimal) row[2]);
    }

    default List<StaySummaryDTO> toStaySummaries(List<Object[]> rows) {
        return rows.stream().map(this::toStaySummary).toList();
    }

    @Mapping(source = "vehicle.plate", target = "plate")
    VehicleReportResponseDTO toVehicleReport(Vehicle vehicle, Long totalStays, Long totalHours, BigDecimal totalCharged,
                                             BigDecimal averageCharge, BigDecimal highestCharge, List<StaySummaryDTO> stays);
}
