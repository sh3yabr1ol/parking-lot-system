package com.xyz.parking.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryPoint {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer entryPointId;
	
	@Column(name = "name")
	private String name;
}
