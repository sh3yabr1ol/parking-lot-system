package com.xyz.parking.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.parking.constant.ParkingSlotType;
import com.xyz.parking.constant.VehicleType;
import com.xyz.parking.dto.ParkingLotDto;
import com.xyz.parking.dto.VehicleDto;
import com.xyz.parking.entities.EntryPoint;
import com.xyz.parking.entities.ParkingLot;
import com.xyz.parking.entities.ParkingSlot;
import com.xyz.parking.entities.Vehicle;
import com.xyz.parking.model.ParkingRequest;
import com.xyz.parking.repository.EntryPointRepository;
import com.xyz.parking.repository.ParkingLotRepository;
import com.xyz.parking.repository.ParkingSlotRepository;
import com.xyz.parking.repository.VehicleRepository;

@Service
public class ParkingLotService {

	@Autowired
	private ParkingLotRepository parkingLotRepository;
	
	@Autowired
	private ParkingSlotRepository parkingSlotRepository;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private EntryPointRepository entryPointRepository;
	
	@Autowired
	private ParkingFareService parkingFareService;
	
	public ParkingLotDto setUpParkingLot(ParkingLot parkingLot) {
		ParkingLot lot = parkingLotRepository.save(parkingLot);
		return new ParkingLotDto(lot.getParkingLotId(), lot.getParkingSlots(), lot.getEntryPoints());
	}
	
	public ParkingLot addEntryPoint(String name, Integer parkingLotId) {
		EntryPoint newEntryPoint = new EntryPoint();
		newEntryPoint.setName(name);
		
		Optional<ParkingLot> parkingLot = parkingLotRepository.findById(parkingLotId);
		if(parkingLot.isPresent()) {
			parkingLot.get().getEntryPoints().add(newEntryPoint);
			parkingLotRepository.save(parkingLot.get());
			return parkingLot.get();
		}
		return null;
	}
	
	public VehicleDto parkVehicle(ParkingRequest parkingRequest) {
		
		Vehicle vehicle = parkingRequest.getVehicle();
		
		String entryPoint= findEntryPoint(parkingRequest.getEntryPointDistance());
		
		if(vehicle.getVehicleType().equals(VehicleType.S)) {
			return parkSVehicle(vehicle, entryPoint);
		} else if (vehicle.getVehicleType().equals(VehicleType.M)) {
			return parkMVehicle(vehicle, entryPoint);
		} else if (vehicle.getVehicleType().equals(VehicleType.L)) {
			return parkLVehicle(vehicle, entryPoint);
		}
		return null;
	}
	
	public String unpark(String plateNumber) {
		
		Vehicle vehicle = vehicleRepository.findByPlateNumber(plateNumber);

		LocalDateTime entryTime = vehicle.getEntryDateTime();
		LocalDateTime exitTime = LocalDateTime.now();
		
		Duration duration = Duration.between(entryTime, exitTime);
		
		Double totalAmount = parkingFareService.calculationFees(vehicle.getParkingSlot().getParkingSlotType(), duration);

		vehicle.setExitDateTime(exitTime);
		vehicle.setTotalFare(totalAmount);
		
		ParkingSlot parkingSlot = vehicle.getParkingSlot();
		parkingSlot.setIsAvailable(true);
		
		vehicleRepository.save(vehicle);
		parkingSlotRepository.save(parkingSlot);
		
		return "Total Parking Fee For Vehicle [" + vehicle.getPlateNumber() 
		+"]: \n Vehicle Type : " + vehicle.getVehicleType()
		+ "\n Parking Slot Type: " + vehicle.getParkingSlot().getParkingSlotType()
		+ "\n Total Hrs Rendered: " + duration.toHours()
		+ "\n = " + totalAmount.toString();
	}
	
	private VehicleDto parkSVehicle(Vehicle vehicle, String entryPoint) {
		
		ParkingSlot smallSlot = getSlot(ParkingSlotType.SP);
		ParkingSlot mediumSlot = getSlot(ParkingSlotType.MP);
		ParkingSlot largeSlot = getSlot(ParkingSlotType.LP);
		
		if(largeSlot == null && mediumSlot == null && smallSlot == null) {
			throw new RuntimeException("No available slots as of the moment");
		}
		
		
		return smallSlot != null ? persist(vehicle, smallSlot, entryPoint) : 
			mediumSlot != null ? persist(vehicle, mediumSlot, entryPoint) : 
			largeSlot != null ? persist(vehicle, largeSlot , entryPoint) : null; 

	}
	
	private VehicleDto parkMVehicle(Vehicle vehicle, String entryPoint) {
		
		ParkingSlot mediumSlot = getSlot(ParkingSlotType.MP);
		ParkingSlot largeSlot = getSlot(ParkingSlotType.LP);
		
		if(largeSlot == null && mediumSlot == null) {
			throw new RuntimeException("No available slots as of the moment");
		}
		
		
		return mediumSlot != null ? persist(vehicle, mediumSlot, entryPoint) : 
			largeSlot != null ? persist(vehicle, largeSlot, entryPoint) : null;
	}
	
	private VehicleDto parkLVehicle(Vehicle vehicle, String entryPoint) {
		
		ParkingSlot slot = getSlot(ParkingSlotType.LP);
    	if(slot != null) {
    		return persist(vehicle, slot, entryPoint);
    	} else {
    		throw new RuntimeException("No more slot for Large vehicles!");
    	}
	}
	
	private ParkingSlot getSlot(ParkingSlotType parkingSlotType) {
		
		List<ParkingSlot> parkingSlots = parkingSlotRepository.findAll();
		
		return parkingSlots.stream()
				.filter(e -> e.getParkingSlotType().equals(parkingSlotType) && e.getIsAvailable())
				.findFirst().orElse(null);
	}
	
	private VehicleDto persist(Vehicle vehicle, ParkingSlot parkingSlot, String entryPoint) {
		
		parkingSlot.setIsAvailable(false);
		vehicle.setParkingSlot(parkingSlot);
		vehicle.setEntryDateTime(LocalDateTime.now());
		
		vehicleRepository.save(vehicle);
		parkingSlotRepository.save(parkingSlot);
		
		return new VehicleDto(vehicle.getVehicleId(), 
				vehicle.getPlateNumber(), 
				vehicle.getVehicleType(), 
				entryPoint,
				vehicle.getParkingSlot().getId(), 
				vehicle.getParkingSlot().getParkingSlotType());
		
	}
	
	private String findEntryPoint(List<Integer> entryPoints) {
		
		List<EntryPoint> entry = entryPointRepository.findAll();
		
		if(entryPoints.size() != entry.size()) {
			throw new RuntimeException("There are only " + entry.size() + " entry points as of the moment...");
		}
		
		int entryPoint = entryPoints.indexOf(Collections.min(entryPoints));
		
		return entry.get(entryPoint).getName();
	}
}
