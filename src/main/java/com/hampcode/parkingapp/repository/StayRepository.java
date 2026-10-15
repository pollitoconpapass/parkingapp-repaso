package com.hampcode.parkingapp.repository;

import com.hampcode.parkingapp.entity.Stay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StayRepository extends JpaRepository<Stay, Long> {
    // TODO

}
