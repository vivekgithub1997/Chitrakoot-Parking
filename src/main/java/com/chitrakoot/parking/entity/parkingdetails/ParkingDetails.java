package com.chitrakoot.parking.entity.parkingdetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ParkingDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "Token-Id")
	private int tokenNo;

	@Column(name = "Token-Validity")
	private String tokenValidity;

	@Column(name = "Vehicle-Number")
	private String vehicleNumber;

	@Column(name = "Vehicle-Class")
	private String vehicleClass;

	@Column(name = "Operator-Name")
	private String operatorName;

	@Column(name = "Entry-Lane-Gate")
	private String entryLaneGate;

	@Column(name = "Entry-Date-Time")
	private String entryDateAndTime;

	@Column(name = "Exit-Lane-Gate")
	private String exitLaneGate;

	@Column(name = "Exit-Date-Time")
	private String exitDateAndTime;
	
	@Column(name = "Duration-Park-Vehicle")
	private String durationParkVehicle;

	@Column(name = "Amount")
	private int amount;

	@Column(name = "Vehicle-Park-Status")
	private String vehicleParkStatus;

}
