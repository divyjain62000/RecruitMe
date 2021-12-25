package com.recruitme.service.domain.drive.dto;

import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DriveResponseDTO extends DataTransferObject {

    private Integer id;
    private String company;
    private String driveName;
    private String salary;
    private String validBranches;
    private String validPrograms;
    private Float required10thCGPA;
    private Float required10thPercentage;
    private Float required12thCGPA;
    private Float required12thPercentage;
    private Float requiredUGCGPA;
    private Float requiredUGPercentage;
    private Float requiredPGCGPA;
    private Float requiredPGPercentage;
    private Short minimumBacklogsAllowed;
    private String driveForGender;
    private LocalDateTime lastDateToApply;
    private String vacancyType;
    private String driveForGraduation;
    private String ugPassoutYearAllow;
    private String pgPassoutYearAllow;
    private Integer minGapAllowInEducation;
    private String otherDelatils;

}
