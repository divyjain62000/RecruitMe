package com.recruitme.service.domain.student.holding.offers.dto;

import com.recruitme.enums.JobType;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;
import org.modelmapper.internal.Pair;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class StudentHoldingOffersDTO extends DataTransferObject {
    private Integer id;
    private Long studentId;
    private String company;
    private JobType jobType;
    private BigDecimal salary;
}
