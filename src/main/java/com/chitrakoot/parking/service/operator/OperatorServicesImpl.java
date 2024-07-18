package com.chitrakoot.parking.service.operator;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chitrakoot.parking.entity.operatordetails.OperatorDetails;
import com.chitrakoot.parking.repo.operator.OperatorDetailsRepo;

@Service
public class OperatorServicesImpl implements OperatorServices {

	private OperatorDetailsRepo operatorDetailsRepo;

	public OperatorServicesImpl(OperatorDetailsRepo operatorDetailsRepo) {
		super();
		this.operatorDetailsRepo = operatorDetailsRepo;
	}

	@Override
	public OperatorDetails createOperator(OperatorDetails operatorDetails) {
		// TODO Auto-generated method stub

		return operatorDetailsRepo.save(operatorDetails);

	}

	@Override
	public OperatorDetails updateOperator(OperatorDetails operatorDetails, Integer operatorId) {
		// TODO Auto-generated method stub
		OperatorDetails operatorDetail = operatorDetailsRepo.findById(operatorId)
				.orElseThrow(() -> new RuntimeException("OPERATOR NOT FOUND...!!"));

		operatorDetail.setOperatorAddress(operatorDetails.getOperatorAddress());
		operatorDetail.setOperatorMobile(operatorDetails.getOperatorMobile());
		operatorDetail.setOperatorName(operatorDetails.getOperatorName());
		return operatorDetailsRepo.save(operatorDetails);

	}

	@Override
	public List<OperatorDetails> getAllOperator() {
		// TODO Auto-generated method stub
		return operatorDetailsRepo.findAll();
	}

	@Override
	public OperatorDetails getSingleOperator(Integer operatorId) {
		// TODO Auto-generated method stub
		return operatorDetailsRepo.findById(operatorId)
				.orElseThrow(() -> new RuntimeException("OPERATOR ID NOT FOUND....!!"));
	}

	@Override
	public boolean deleteoperator(Integer operatorId) {
		// TODO Auto-generated method stub

		boolean status = false;
		operatorDetailsRepo.deleteById(operatorId);
		status = true;
		return status;
	}

}
