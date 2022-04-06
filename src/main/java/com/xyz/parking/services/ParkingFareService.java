package com.xyz.parking.services;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.xyz.parking.constant.ParkingSlotType;

@Service
public class ParkingFareService {

	private int flatRate = 40; 
	private int fullChunk = 5000;
	
	private int getRatePerVehicleType(ParkingSlotType parkingSlotType) {
		
		switch (parkingSlotType) {
		case SP:
			return 20;
		case MP:
			return 60;
		case LP:
			return 100;
		default:
			throw new RuntimeException("Invalid Rate");
		}
	}

	public Double calculationFees(ParkingSlotType parkingSlotType, Duration duration) {
		
		Double totalFees = 0.0;
		int totalHours = (int) (duration.toHours());

		int rate = getRatePerVehicleType(parkingSlotType);
		
		if(totalHours <= 3) {
			totalFees = (double) (flatRate);
		} else if (totalHours > 3 && totalHours < 24) {
			totalHours = totalHours - 3;
			totalFees = (double) (totalHours * rate + flatRate);
		} else if (totalHours >= 24) {
			totalHours = totalHours - 24;
			int hoursExceeded = totalHours * rate;
			totalFees = (double) (fullChunk + hoursExceeded + flatRate);
		} 
		return totalFees;
	}
}
