package com.chitrakoot.parking.repo.operator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chitrakoot.parking.entity.operatordetails.OperatorDetails;

@Repository
public interface OperatorDetailsRepo extends JpaRepository<OperatorDetails, Integer> {

}
