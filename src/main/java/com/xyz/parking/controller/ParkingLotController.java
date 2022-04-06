package com.xyz.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.parking.dto.ParkingLotDto;
import com.xyz.parking.dto.VehicleDto;
import com.xyz.parking.entities.ParkingLot;
import com.xyz.parking.model.ParkingRequest;
import com.xyz.parking.services.ParkingLotService;

@RestController
@RequestMapping("/xyz")
public class ParkingLotController {

	@Autowired
	private ParkingLotService parkingLotService;
	
	
	@PostMapping("/parkingLot")
	public ResponseEntity<ParkingLotDto> setUpParkingLot(@RequestBody ParkingLot parkingLot) {
		return new ResponseEntity<>(parkingLotService.setUpParkingLot(parkingLot), HttpStatus.CREATED);
	}
	
	@PostMapping("/addEntryPoint/{name}/{parkingLotId}")
	public ParkingLot addEntryPoint(@PathVariable String name, @PathVariable Integer parkingLotId) {
		return parkingLotService.addEntryPoint(name, parkingLotId);
	}
	
	@PostMapping("/park")
	public ResponseEntity<VehicleDto> park(@RequestBody ParkingRequest parkingRequest) {
		return new ResponseEntity<>(parkingLotService.parkVehicle(parkingRequest), HttpStatus.CREATED);
	}
	
	@PostMapping("/unpark")
	public ResponseEntity<String> park(@RequestParam String plateNumber) {
		return new ResponseEntity<>(parkingLotService.unpark(plateNumber), HttpStatus.CREATED);
	}
}
