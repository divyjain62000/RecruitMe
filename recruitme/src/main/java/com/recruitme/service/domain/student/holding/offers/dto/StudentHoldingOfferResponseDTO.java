package com.recruitme.service.domain.student.holding.offers.dto;

import com.recruitme.domain.Student;
import com.recruitme.enums.JobType;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StudentHoldingOfferResponseDTO  extends DataTransferObject {
    private Student student;
    List<StudentHoldingOffersDTO> studentHoldingOffersDTOList;
}