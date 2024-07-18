package com.chitrakoot.parking.repo.parking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.chitrakoot.parking.entity.parkingdetails.ParkingDetails;

@Repository
public interface ParkingDetailsRepo extends JpaRepository<ParkingDetails, Integer> {

	ParkingDetails findByVehicleNumber(String vehicleNumber);

	@Query("SELECT p FROM ParkingDetails p WHERE "
			+ "LOWER(p.vehicleNumber) LIKE LOWER(CONCAT('%', :searchText, '%')) OR "
			+ "LOWER(p.entryDateAndTime) LIKE LOWER(CONCAT('%', :searchText, '%')) OR "
			+ "LOWER(p.vehicleParkStatus) LIKE LOWER(CONCAT('%', :searchText, '%')) OR "
			+ "LOWER(p.operatorName) LIKE LOWER(CONCAT('%', :searchText, '%'))")
	List<ParkingDetails> findParkingDetailsBySearchText(@RequestParam("searchText") String searchText);

}
