package com.xyz.parking.repository;

import org.springframework.stereotype.Repository;

import com.xyz.parking.entities.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer>{

	public Vehicle findByPlateNumber(String plateNumber);
}
