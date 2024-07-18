package com.chitrakoot.parking.entity.operatordetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OperatorDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "Operator-Id")
	private int operatorId;
	@Column(name = "Operator-Name")
	private String operatorName;
	@Column(name = "Operator-Address")
	private String operatorAddress;
	@Column(name = "Operator-Mobile")
	private long operatorMobile;

}
