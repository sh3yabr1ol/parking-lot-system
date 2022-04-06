package com.xyz.parking.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.xyz.parking.constant.ParkingSlotType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parking_slot")
public class ParkingSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "parking_slot_type")
	private ParkingSlotType parkingSlotType; 

	@OneToMany(targetEntity=Vehicle.class, 
			mappedBy="parkingSlot",
			cascade=CascadeType.ALL, 
			fetch = FetchType.LAZY)
	private List<Vehicle> vehicle; 
	
	@Column(name = "is_available")
	private Boolean isAvailable = true;
	
}
