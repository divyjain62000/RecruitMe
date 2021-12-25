package com.recruitme.service.domain.placement.faculty.dto;

import com.recruitme.domain.Branch;
import com.recruitme.domain.Program;
import lombok.Data;

import javax.persistence.*;

@Data
public class PlacementPreparationFacultyDTO {

    private Long id;
    private String name;
    private String primaryEmail;
    private String primaryMobileNumber;
    private String passwordKey;
    private String password;
    private Character gender;
    private Integer programId;
    private Integer branchId;

}
