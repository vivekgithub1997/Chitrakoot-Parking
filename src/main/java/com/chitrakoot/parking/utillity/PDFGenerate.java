package com.chitrakoot.parking.utillity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chitrakoot.parking.entity.parkingdetails.ParkingDetails;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PDFGenerate {

	@Autowired
	private MailService mailService;

	Logger logger = LoggerFactory.getLogger(PDFGenerate.class);

	public void generate(ParkingDetails parkingDetails, String toMail) throws DocumentException, IOException {

		try {

			String path = "D:\\All Workspace\\eclipse workspace\\Chitrakoot-Parking-System\\src\\main\\resources\\ExitVehiclePdf\\"
					+ parkingDetails.getVehicleNumber() + ".pdf";
//			String barcode = "C:\\Users\\spider\\Documents\\regHospitalweb\\barcode\\barcode.png";
			Document document = new Document(PageSize.A4);

			PdfWriter.getInstance(document, new FileOutputStream(path));

			document.open();
			Paragraph para01 = new Paragraph(
					"            ------------------------------------- Parking Exit Receipt -----------------------------------");
			Paragraph para001 = new Paragraph("\n");
			Paragraph para0 = new Paragraph("Exit Date- :  " + parkingDetails.getExitDateAndTime());
			Paragraph para00 = new Paragraph("\n");
			Paragraph para000 = new Paragraph("TOKEN ID- : " + parkingDetails.getTokenNo());
			Paragraph para00001 = new Paragraph("Operator Name- :  " + parkingDetails.getOperatorName());
			Paragraph para0000 = new Paragraph("\n");
			Paragraph para = new Paragraph(
					"                                                     Welcome To Chitrakoot Parking System\n"
							+ "Address-: Sitapur chitrakoot");
			Paragraph para1 = new Paragraph("Email-: " + "chitrakootparkinginfo@gmail.com");
			document.add(para01);
			document.add(para001);
			document.add(para00001);
			document.add(para0);
			document.add(para00);
			document.add(para000);
			document.add(para0000);
			document.add(para);
			document.add(para1);

			Paragraph para3 = new Paragraph("\n");
			Paragraph para4 = new Paragraph(
					"                        ---------------------------------- Parking Details ---------------------------------");
			Paragraph para5 = new Paragraph("\n");
			document.add(para3);
			document.add(para4);
			document.add(para5);
			// table patient

			PdfPTable pt = new PdfPTable(6);
			PdfPCell c1 = new PdfPCell(new Phrase("Vehicle Number"));
			pt.addCell(c1);
			c1 = new PdfPCell(new Phrase("Vehicle Class"));
			pt.addCell(c1);
			c1 = new PdfPCell(new Phrase("Total Duration"));
			pt.addCell(c1);
			c1 = new PdfPCell(new Phrase("Entry Date"));
			pt.addCell(c1);
			c1 = new PdfPCell(new Phrase("Exit Date"));
			pt.addCell(c1);
			c1 = new PdfPCell(new Phrase("Amount"));
			pt.addCell(c1);
			pt.setHeaderRows(1);
			pt.addCell(parkingDetails.getVehicleNumber());
			pt.addCell(parkingDetails.getVehicleClass());
			pt.addCell(parkingDetails.getDurationParkVehicle());
			pt.addCell(parkingDetails.getEntryDateAndTime());
			pt.addCell(parkingDetails.getExitDateAndTime());
			pt.addCell(Integer.toString(parkingDetails.getAmount()));

			document.add(pt);

			Paragraph para9 = new Paragraph("\n");
			Paragraph para10 = new Paragraph("Thank You For Choose My Services\n");
			Paragraph para11 = new Paragraph("Regard " + "Chitrakoot Parking system");
			document.add(para9);
			document.add(para10);
			document.add(para11);
			Paragraph para12 = new Paragraph("\n");

			Paragraph para13 = new Paragraph(
					"       -----------------------------------------------------------------------------------------------------------------------");
			Paragraph para14 = new Paragraph("\n");
			Paragraph para15 = new Paragraph(
					"                                                      Welcome To Chitrakoot Parking System                                          \n                                                       Address-: "
							+ "Sitapur Chitrakoot");
			Paragraph para17 = new Paragraph("\n");

			Paragraph para16 = new Paragraph("                                                       Email-: "
					+ "chitrakootparkinginfo@gmail.com");
			Paragraph para18 = new Paragraph("\n");
			document.add(para12);
			document.add(para13);
			document.add(para14);
			document.add(para15);
			document.add(para16);
			document.add(para17);
			document.add(para18);

			System.out.println("Please Wait..................!!");
			document.close();

			// SEND PDF

			mailService.sendMailWithAttachment(toMail, "Vehicle Exit Recipt", "Thank you for choosing our service...!!",
					path);
			// this.sendemail.sendmailwithattachment(ambulenceBooking.getEmail(), "Ambulence
			// Booking Successfully", "Chitrakoot Hospital", path);
			

			File file = new File(path);
			boolean delete = file.delete();

			if (delete) {
				logger.info("MAIL SEND SUCCESSFULLY...!! And File Deleted.....!!");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
