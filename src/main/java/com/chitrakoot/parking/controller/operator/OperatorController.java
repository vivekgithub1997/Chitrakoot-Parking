package com.chitrakoot.parking.controller.operator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chitrakoot.parking.entity.operatordetails.OperatorDetails;
import com.chitrakoot.parking.service.operator.OperatorServices;

@RestController
@RequestMapping("/operator")
public class OperatorController {

	private OperatorServices operatorServices;

	public OperatorController(OperatorServices operatorServices) {
		super();
		this.operatorServices = operatorServices;
	}

	@PostMapping("v1/save")
	public ResponseEntity<OperatorDetails> createOperator(@RequestBody OperatorDetails operatorDetails) {

		OperatorDetails operator = operatorServices.createOperator(operatorDetails);

		return new ResponseEntity<OperatorDetails>(operator, HttpStatus.CREATED);
	}
	
	
	

}
