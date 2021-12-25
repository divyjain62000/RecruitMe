package com.recruitme.service.domain.student.dto;

import com.recruitme.domain.Address;
import com.recruitme.domain.Education;
import com.recruitme.domain.Parents;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class StudentDTO extends DataTransferObject {

    private Long id;
    private String enrollmentNumber;
    private String passwordKey;
    private String password;
    private String name;
    private String primaryEmail;
    private String secondaryEmail;
    private String primaryMobileNumber;
    private String secondaryMobileNumber;
    private Integer addressId;
    private Integer parentsId;
    private Boolean isPlaceForFulltime;
    private Boolean isPlaceForInternship;
    private Boolean isBlacklisted;
    private String reasonForBlacklist;
    private Boolean isValidPrimaryEmail;
    private Boolean isValidSecondaryEmail;
    private Boolean isValidPrimaryMobileNumber;
    private Boolean isValidSecondaryMobileNumber;
    private Character gender;
    private Boolean isIndian;
    private String mouCompanyReferenceId;
    private Integer educationId;
    private Boolean isAnyCriminalCase;
    private String criminalCaseDescription;
    private Boolean isDisability;
    private String disability;

}
