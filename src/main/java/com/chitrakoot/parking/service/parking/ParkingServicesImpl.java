package com.chitrakoot.parking.service.parking;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chitrakoot.parking.entity.exitdetails.ExitVehicleDetails;
import com.chitrakoot.parking.entity.operatordetails.OperatorDetails;
import com.chitrakoot.parking.entity.parkingdetails.ParkingDetails;
import com.chitrakoot.parking.repo.exitvehicle.ExitVehicleDetailsRepo;
import com.chitrakoot.parking.repo.operator.OperatorDetailsRepo;
import com.chitrakoot.parking.repo.parking.ParkingDetailsRepo;
import com.chitrakoot.parking.utillity.PDFGenerate;
import com.itextpdf.text.DocumentException;

@Service
public class ParkingServicesImpl implements ParkingServices {

	Logger logger = LoggerFactory.getLogger(ParkingServicesImpl.class);

	@Autowired
	private ParkingDetailsRepo parkingDetailsRepo;

	private OperatorDetailsRepo operatorDetailsRepo;

	private ExitVehicleDetailsRepo exitVehicleDetailsRepo;

	@Autowired
	private PDFGenerate pdfTesting;

	public ParkingServicesImpl(OperatorDetailsRepo operatorDetailsRepo, ExitVehicleDetailsRepo exitVehicleDetailsRepo) {
		super();

		this.operatorDetailsRepo = operatorDetailsRepo;
		this.exitVehicleDetailsRepo = exitVehicleDetailsRepo;
	}

	@Override
	public String newEntry(String vehicleNumber, String vehicleClass, int operatorId) {
		// TODO Auto-generated method stub
		OperatorDetails operatorDetails = null;
		ParkingDetails parkingDetails = new ParkingDetails();

		// find operator ID and get the record
		Optional<OperatorDetails> operatorById = operatorDetailsRepo.findById(operatorId);
		if (operatorById.isPresent()) {
			operatorDetails = operatorById.get();
		}

		// checking vehicle already park or not
		ParkingDetails getVehicle = null;
		getVehicle = parkingDetailsRepo.findByVehicleNumber(vehicleNumber);

		if (getVehicle != null && getVehicle.getVehicleParkStatus().equals("EXIT")) {

			parkingDetailsRepo.deleteById(getVehicle.getTokenNo());

		}

		if (getVehicle != null && getVehicle.getVehicleNumber().equals(vehicleNumber)
				&& getVehicle.getVehicleParkStatus().equals("PARK")) {

			return "Duplicate Entry:: Vehicle already parked...!!";
		} else {
			// set vehicle number
			parkingDetails.setVehicleNumber(vehicleNumber);

			// set vehicle class
			String vahicleClass = vahicleClass(vehicleClass);
			parkingDetails.setVehicleClass(vahicleClass);

			// set operator name
			parkingDetails.setOperatorName(operatorDetails.getOperatorName());

			// set token Validity
			Date date = new Date();
			long updatedDate = date.getTime() + (720 * 60 * 1000);
			date = new Date(updatedDate);
			SimpleDateFormat formatTime = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
			String format = formatTime.format(date);

			// Joining two String into one
			StringJoiner joiner = new StringJoiner(" ");
			joiner.add(format.toString());
			joiner.add("12 hour");

			parkingDetails.setTokenValidity(joiner.toString());

			// set Entry Date And Time
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
			String formattedDate = dateFormat.format(new Date()).toString();
			parkingDetails.setEntryDateAndTime(formattedDate);

			// set entry Gate
			int entryGate = getEntryGate();
			parkingDetails.setEntryLaneGate("LANE-IN: " + entryGate);

			// set exit Gate
			parkingDetails.setExitLaneGate("Not Provide");

			// set vehicle amount
			int amountCalculator = amountCalculator(vehicleClass);
			parkingDetails.setAmount(amountCalculator);

			// set parking status

			parkingDetails.setVehicleParkStatus("PARK");

			// finally save the data into the database
			ParkingDetails save = parkingDetailsRepo.save(parkingDetails);

			if (save != null) {

				return "VEHICLE ENTRY SUCCESSFULLY";

			}
		}

		return "SOMETHING WENT WRONG.....!!";
	}

	@Override
	public String exitEntry(String vehicleNumber, String toMail) {

		// Find Vehicle based On VehicleNumber
		ParkingDetails getVehicle = null;
		getVehicle = parkingDetailsRepo.findByVehicleNumber(vehicleNumber);

		if (getVehicle != null && getVehicle.getVehicleNumber().equals(vehicleNumber)) {

			if (getVehicle.getVehicleParkStatus().equals("EXIT")) {

				return "Vehicle Alread Exit...!! ";
			} else {
				int exitGate = getExitGate();

				getVehicle.setExitLaneGate("LANE OUT: " + exitGate);

				// set exit Date And Time
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
				String formattedDate = dateFormat.format(new Date()).toString();

				getVehicle.setExitDateAndTime(formattedDate);

				// set parking status
				getVehicle.setVehicleParkStatus("EXIT");
				ParkingDetails save = parkingDetailsRepo.save(getVehicle);

				// set total parked vehicle Duration
				String durationParkedVehicle = durationParkedVehicle(getVehicle.getEntryDateAndTime(), formattedDate);
				getVehicle.setDurationParkVehicle(durationParkedVehicle);

				// saving exitVehicleDeatils to DB
				if (save != null) {

					ExitVehicleDetails exitVehicleDetails = new ExitVehicleDetails();
					String entryDateAndTime = getVehicle.getEntryDateAndTime();
					String entryLaneGate = getVehicle.getEntryLaneGate();

					// set Entry Date Time And Gate
					StringJoiner entryDateTimeGate = new StringJoiner(" | ", entryDateAndTime + " ", entryLaneGate);

					// set Exit Date Time And Gate
					String exitDateAndTime = getVehicle.getExitDateAndTime();
					String exitLaneGate = getVehicle.getExitLaneGate();
					StringJoiner exitDateTimeGate = new StringJoiner(" | ", exitDateAndTime + " ", exitLaneGate);

					// set entry and exit Date Time and Gate to setter method
					exitVehicleDetails.setEntryDateTime(entryDateTimeGate.toString());
					exitVehicleDetails.setExitDateTime(exitDateTimeGate.toString());

					// set token
					int tokenNo = getVehicle.getTokenNo();

					String token = Integer.toString(tokenNo);

					exitVehicleDetails.setTokenId(token);

					// set vehicle Number
					exitVehicleDetails.setVehicleNumber(getVehicle.getVehicleNumber());

					// set Vehicle Class

					exitVehicleDetails.setVehicleClass(getVehicle.getVehicleClass());

					// set operator Name

					exitVehicleDetails.setOperatorName(getVehicle.getOperatorName());

					// set Vehicle duration Time

					exitVehicleDetails.setDurationParkVehicle(getVehicle.getDurationParkVehicle());

					exitVehicleDetailsRepo.save(exitVehicleDetails);
					System.out.println(exitVehicleDetails);

					// generating PDF

					logger.info("generatePdf Method Started... ::");

					try {
						pdfTesting.generate(save, toMail);
						logger.info("generatePdf Method executed... ::");
						logger.info("PDF Generated Successfuly... ::");

					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logger.info("generatePdf Method executed... ::");
					logger.info("PDF Generated Successfuly... ::");
					return "Vehicle Successfully Exit...!!";

				}

			}

		} else {
			return "Vehicle Number Not Found...!!";
		}

		return "SOMETHING WENT WRONG...!!";

	}

	/*
	 * @Override public List<ParkingDetails> filterDataByStateName(String stateName)
	 * { // TODO Auto-generated method stub
	 * 
	 * List<ParkingDetails> all = parkingDetailsRepo.findAll();
	 * 
	 * List<ParkingDetails> getRecordByStateName = new ArrayList<ParkingDetails>();
	 * 
	 * for (ParkingDetails details : all) {
	 * 
	 * if (details.getVehicleNumber().subSequence(0, 2).equals(stateName)) {
	 * 
	 * getRecordByStateName.add(details);
	 * 
	 * } } return getRecordByStateName;
	 * 
	 * }
	 */

	@Override
	public List<ParkingDetails> findParkingDetailsBySearchText(String searchText) {

		List<ParkingDetails> parkingDetailsBySearchText = parkingDetailsRepo.findParkingDetailsBySearchText(searchText);

		return parkingDetailsBySearchText;
	}

	// All private Methods

	// total time calculation
	private static String durationParkedVehicle(String enrtyDate, String exitDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy hh.mm a");

		// Parse the input date strings
		LocalDateTime dateTime1 = LocalDateTime.parse(enrtyDate, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(exitDate, formatter);

		// Calculate the difference between the two dates
		Duration duration = Duration.between(dateTime1, dateTime2);

		// Get the hours and minutes
		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;

		// Use StringJoiner to join the formatted date-time strings
		StringJoiner joiner = new StringJoiner(" : ");
		joiner.add(Long.toString(hours).concat(" Hour"));
		joiner.add(Long.toString(minutes).concat(" Minutes"));

		return joiner.toString();
	}

	// find Vehicle

	private static String vahicleClass(String vehicleClass) {

		if (vehicleClass.equalsIgnoreCase("2 Wheeler")) {
			return "2 WHEELER";
		} else {
			return "4 WEELER";
		}
	}

	private static int amountCalculator(String vehicleClass) {

		if (vehicleClass.equalsIgnoreCase("2 Wheeler")) {
			return 10;
		} else {
			return 20;
		}

	}

	private static int getEntryGate() {

		Random random = new Random();
		int nextInt = random.nextInt(1, 5);
		return nextInt;

	}

	private static int getExitGate() {

		Random random = new Random();
		int nextInt = random.nextInt(1, 5);
		return nextInt;

	}

}
