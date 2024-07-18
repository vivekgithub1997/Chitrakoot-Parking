package com.chitrakoot.parking.entity.exitdetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ExitVehicleDetails {

	@Id
	@Column(name = "Token-Id")
	private String tokenId;
	
	@Column(name = "Vehicle-Number")
	private String vehicleNumber;
	
	@Column(name = "Vehicle-Class")
	private String vehicleClass;
	
	@Column(name = "Entry-Date-Time")
	private String entryDateTime;
	
	@Column(name = "Exit-Date-Time")
	private String exitDateTime;
	
	@Column(name = "Operator-Name")
	private String OperatorName;
	
	@Column(name = "Duration-Park-Vehicle")
	private String durationParkVehicle;

}
