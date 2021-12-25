package com.recruitme.service.domain.interview.experience.dto;

import com.recruitme.domain.Student;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InterviewExperienceDTO extends DataTransferObject {

    private Integer id;
    private Long studentId;
    private String company;
    private Short passoutYear;
    private BigDecimal salary;
    private Boolean isFullTimeVacancy;
    private Boolean isInternshipVacancy;
    private String experience;
    private Boolean ugStudent;
    private Boolean pgStudent;

}
