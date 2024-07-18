package com.chitrakoot.parking.controller.parking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chitrakoot.parking.entity.parkingdetails.ParkingDetails;
import com.chitrakoot.parking.service.parking.ParkingServices;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/parking")
public class ParkingController {

	private ParkingServices parkingServices;

	public ParkingController(ParkingServices parkingServices) {
		super();
		this.parkingServices = parkingServices;
	}

	private Logger logger = LoggerFactory.getLogger(ParkingController.class);

	@PostMapping("/v1/vehicle/entry/{vehicleNumber}/{vehicleClass}/{operatorId}")
	public ResponseEntity<String> newEntry(@PathVariable String vehicleNumber, @PathVariable String vehicleClass,
			@PathVariable Integer operatorId) {
		logger.info("newEntry Method started....!! :: ");
		String newEntry = parkingServices.newEntry(vehicleNumber.toUpperCase(), vehicleClass, operatorId);
		logger.info("newEntry Method Executed....!! " + newEntry);
		return new ResponseEntity<String>(newEntry, HttpStatus.CREATED);

	}

	@GetMapping("/v1/vehicle/exit")
	public ResponseEntity<String> exitEntry(@RequestParam("VehicleNumber") String vehicleNumber,
			@RequestParam("toMail") String toMail) {
		logger.info("exitEntry Method started....!! :: ");
		String exitEntry = parkingServices.exitEntry(vehicleNumber.toUpperCase(), toMail);
		logger.info("exitEntry Method Executed....!! " + exitEntry);
		return new ResponseEntity<>(exitEntry, HttpStatus.CREATED);
	}

	/*
	 * @GetMapping("/v1/vehicle/search/state-name") public
	 * ResponseEntity<List<ParkingDetails>> getDataByStateNameSymbol(
	 * 
	 * @RequestParam("state-name") String stateNameSymbol) {
	 * 
	 * List<ParkingDetails> filterDataByStateName = parkingServices
	 * .filterDataByStateName(stateNameSymbol.toUpperCase());
	 * 
	 * return new ResponseEntity<List<ParkingDetails>>(filterDataByStateName,
	 * HttpStatus.OK); }
	 */

	@GetMapping("/v1/vehicle/searchText")
	public ResponseEntity<List<ParkingDetails>> getDataBySearchText(@RequestParam("searchText") String searchText) {
		logger.info("getDataBySearchText Method started....!! :: ");
		List<ParkingDetails> parkingDetailsBySearchText = parkingServices.findParkingDetailsBySearchText(searchText);
		if (!parkingDetailsBySearchText.isEmpty()) {
			// logger.info("getDataBySearchText Method ....!! :: ", "Method
			// Executed....!!");
			logger.info("getDataBySearchText Method Executed ....!! :: " + "Search By " + searchText);
			return ResponseEntity.ok(parkingDetailsBySearchText);

		} else {
			logger.info("getDataBySearchText Method Executed....!! :: NO Conetent ");
			return ResponseEntity.noContent().build();
		}

	}
}
