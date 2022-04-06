package com.xyz.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.parking.constant.ParkingSlotType;
import com.xyz.parking.entities.ParkingSlot;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> {

	public ParkingSlot findByParkingSlotType(ParkingSlotType parkingSlotType);
}
