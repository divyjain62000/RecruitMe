package com.recruitme.service.domain.interview.experience.dto;

import com.recruitme.domain.Student;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InterviewExperienceResponseDTO extends DataTransferObject {

    private Integer id;
    private String studentName;
    private String company;
    private Short passoutYear;
    private BigDecimal salary;
    private Boolean isFullTimeVaccany;
    private Boolean isInternshipVaccancy;
    private String experience;
    private boolean ugStudent;
    private boolean pgStudent;
    private String program;
    private String branch;

}
