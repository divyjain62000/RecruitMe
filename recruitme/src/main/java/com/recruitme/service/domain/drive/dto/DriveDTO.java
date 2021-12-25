package com.recruitme.service.domain.drive.dto;

import com.recruitme.domain.PlacementCoordinator;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DriveDTO extends DataTransferObject {

    private Integer id;
    private String company;
    private String driveName;
    private BigDecimal minPackage;
    private BigDecimal maxPackage;
    private List<Integer> validBranches;
    private List<Integer> validPrograms;
    private Float required10thCGPA;
    private Float required10thPercentage;
    private Float required12thCGPA;
    private Float required12thPercentage;
    private Float requiredUGCGPA;
    private Float requiredUGPercentage;
    private Float requiredPGCGPA;
    private Float requiredPGPercentage;
    private Short minimumBacklogsAllowed;
    private Boolean isOnlyForGirls;
    private Boolean isOnlyForBoys;
    private LocalDateTime lastDateToApply;
    private Boolean isFullTimeVaccancy;
    private Boolean isInternshipVaccancy;
    private Boolean onlyForUgStudents;
    private Boolean onlyForPgStudents;
    private Boolean forBothUgPgStudents;
    private List<Integer> ugPassoutYearAllow;
    private List<Integer> pgPassoutYearAllow;
    private Long uploadedById;
    private Integer minGapAllowInEducation;
    private String otherDelatils;

}
