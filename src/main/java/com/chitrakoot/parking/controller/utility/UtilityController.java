package com.chitrakoot.parking.controller.utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chitrakoot.parking.utillity.PDFGenerate;
import com.chitrakoot.parking.utillity.PDFGenerateBasedOnSearchText;
import com.itextpdf.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/generate")
public class UtilityController {

	@Autowired
	private PDFGenerateBasedOnSearchText pdfGenerate;

	Logger logger = LoggerFactory.getLogger(UtilityController.class);

	@GetMapping("/pdf")
	public String generatePdf(HttpServletResponse response) throws DocumentException, IOException {
		logger.info("generatePdf Method Started... ::");
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
		String currentDateTime = dateFormat.format(new Date());
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
		response.setHeader(headerkey, headervalue);

		pdfGenerate.generate(response);
		logger.info("generatePdf Method Executed... ::" + "PDF GENERATE SUCCESSFULLY....!!");
		return "PDF GENERATE SUCCESSFULLY....!!";

	}

}
