package com.xyz.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.parking.entities.EntryPoint;

@Repository
public interface EntryPointRepository extends JpaRepository<EntryPoint, Integer>{

}
