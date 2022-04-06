package com.xyz.parking.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xyz.parking.constant.VehicleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles")
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer vehicleId;
	
	@Column(name = "plate_number")
	private String plateNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "vehicle_type")
	private VehicleType vehicleType;
	
	@Column(name = "entry_date_time")
	private LocalDateTime entryDateTime;

	@Column(name = "exit_date_time")
	private LocalDateTime exitDateTime;

	@ManyToOne
	@JoinColumn(name="parking_slot_id")
	private ParkingSlot parkingSlot;
	
	@Column(name = "total_fare")
	private Double totalFare;
}
