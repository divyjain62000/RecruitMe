package com.recruitme.service.domain.placement.coordinator.dto;

import com.recruitme.domain.Branch;
import com.recruitme.domain.Program;
import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class PlacementCoordinatorDTO extends DataTransferObject {

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
