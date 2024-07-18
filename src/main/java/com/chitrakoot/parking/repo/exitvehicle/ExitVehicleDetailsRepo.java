package com.chitrakoot.parking.repo.exitvehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chitrakoot.parking.entity.exitdetails.ExitVehicleDetails;

@Repository
public interface ExitVehicleDetailsRepo extends JpaRepository<ExitVehicleDetails, String> {

}
