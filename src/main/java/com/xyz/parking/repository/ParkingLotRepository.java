package com.xyz.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.parking.entities.ParkingLot;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Integer> {

}
