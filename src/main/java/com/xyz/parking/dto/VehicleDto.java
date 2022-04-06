package com.xyz.parking.dto;

import com.xyz.parking.constant.ParkingSlotType;
import com.xyz.parking.constant.VehicleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {

	private Integer vehicleId;
	private String plateNumber;
	private VehicleType vehicleType;
	private String parkEntryPoint;
	private Integer parkingSlotId;
	private ParkingSlotType parkingSlotType;
}
