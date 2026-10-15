package com.hampcode.parkingapp.mapper;

import com.hampcode.parkingapp.dto.response.StayResponseDTO;
import com.hampcode.parkingapp.entity.Stay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StayMapper {

    @Mapping(source = "vehicle.plate", target = "plate")
    @Mapping(source = "lot.name", target = "lotName")
    StayResponseDTO toResponse(Stay stay);
}
