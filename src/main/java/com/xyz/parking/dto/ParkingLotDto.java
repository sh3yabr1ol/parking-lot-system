package com.xyz.parking.dto;

import java.util.List;

import com.xyz.parking.entities.EntryPoint;
import com.xyz.parking.entities.ParkingSlot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotDto {

	private Integer parkingLotId;
	private List<ParkingSlot> parkingSlots; 
	private List<EntryPoint> entryPoints;
}
