package com.chitrakoot.parking.service.operator;

import java.util.List;

import com.chitrakoot.parking.entity.operatordetails.OperatorDetails;

public interface OperatorServices {
	
	public OperatorDetails createOperator(OperatorDetails operatorDetails);
	
	public OperatorDetails updateOperator(OperatorDetails operatorDetails,Integer operatorId);
	
	public List<OperatorDetails> getAllOperator();
	
	public OperatorDetails getSingleOperator(Integer operatorId);
	
	public boolean deleteoperator(Integer operatorId);

}
