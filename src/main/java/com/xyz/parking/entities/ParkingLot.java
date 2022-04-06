package com.xyz.parking.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer parkingLotId;
	
	@Column(name = "parking_slots")
	@OneToMany(
		    mappedBy = "id", 
		    orphanRemoval = true,
		    cascade = CascadeType.ALL)
	private List<ParkingSlot> parkingSlots;
	
	@Column(name = "parking_entry_points")
	@OneToMany(
		    mappedBy = "entryPointId", 
		    orphanRemoval = true,
		    cascade = CascadeType.ALL)
	private List<EntryPoint> entryPoints;
}
