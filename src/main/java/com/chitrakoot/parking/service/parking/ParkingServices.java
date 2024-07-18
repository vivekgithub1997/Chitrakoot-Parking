package com.chitrakoot.parking.service.parking;

import java.util.List;

import com.chitrakoot.parking.entity.parkingdetails.ParkingDetails;

public interface ParkingServices {

	public String newEntry(String vehicleNumber, String vehicleClass, int operatorId);

	public String exitEntry(String vehicleNumber, String toMail);

	// public List<ParkingDetails> filterDataByStateName(String stateName);

	List<ParkingDetails> findParkingDetailsBySearchText(String searchText);

}
