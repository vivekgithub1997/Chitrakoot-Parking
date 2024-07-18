package com.chitrakoot.parking.utillity;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chitrakoot.parking.entity.parkingdetails.ParkingDetails;
import com.chitrakoot.parking.repo.parking.ParkingDetailsRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PDFGenerateBasedOnSearchText {

	private ParkingDetailsRepo parkingDetailsRepo;

	public PDFGenerateBasedOnSearchText(ParkingDetailsRepo parkingDetailsRepo) {
		super();
		this.parkingDetailsRepo = parkingDetailsRepo;
	}

	public void generate(HttpServletResponse response) throws DocumentException, IOException {

		List<ParkingDetails> parkingdetails = parkingDetailsRepo.findAll();

		// Creating the Object of Document
		Document document = new Document(PageSize.A4);

		// Getting instance of PdfWriter
		PdfWriter.getInstance(document, response.getOutputStream());

		// Opening the created document to modify it
		document.open();

		// Creating font
		// Setting font style and size
		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);

		// Creating paragraph
		Paragraph paragraph = new Paragraph("List Of Parking Details", fontTiltle);

		// Aligning the paragraph in document
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		// Adding the created paragraph in document
		document.add(paragraph);

		// Creating a table of 3 columns
		PdfPTable table = new PdfPTable(8);

		// Setting width of table, its columns and spacing
		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 5, 5, 5, 5, 5, 5, 5, 5 });
		table.setSpacingBefore(5);

		// Create Table Cells for table header
		PdfPCell cell = new PdfPCell();

		// Setting the background color and padding
		cell.setBackgroundColor(CMYKColor.GRAY);
		cell.setPadding(5);

		// Creating font
		// Setting font style and size
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.WHITE);

		// Adding headings in the created table cell/ header
		// Adding Cell to table
		cell.setPhrase(new Phrase("TOKEN ID", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("VEHICLE NUMBER", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("VEHICLE CLASS", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("OPERATOR NAME", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("ENTRY DATE TIME", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("EXIT DATE TIME", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("VEHICLE PARK DURATION", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("CURRENT VEHICLE STATUS", font));
		table.addCell(cell);

		// Iterating over the list of parking
		for (ParkingDetails parkingDetails2 : parkingdetails) {

			table.addCell(String.valueOf(parkingDetails2.getTokenNo()));

			table.addCell(parkingDetails2.getVehicleNumber());

			table.addCell(parkingDetails2.getVehicleClass());

			table.addCell(parkingDetails2.getOperatorName());

			table.addCell(parkingDetails2.getEntryDateAndTime());

			table.addCell(parkingDetails2.getExitDateAndTime());

			table.addCell(parkingDetails2.getDurationParkVehicle());

			// String vehicleParkStatus = ;
			if (parkingDetails2.getVehicleParkStatus().equals("PARK")) {

				table.addCell("PARK");
			} else {

				table.addCell("EXIT");
			}

		}

		// Adding the created table to document
		document.add(table);

		// Closing the document
		document.close();
	}

}
