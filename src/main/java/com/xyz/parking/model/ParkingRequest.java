package com.xyz.parking.model;

import java.util.List;

import com.xyz.parking.entities.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingRequest {

	private Vehicle vehicle;
	private List<Integer> entryPointDistance;
}
