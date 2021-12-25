package com.recruitme.service.domain.placement.coordinator.dto;

import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;

@Data
public class PlacementCoordinatorResponseDTO extends DataTransferObject {

    private Long id;
    private String name;
    private String primaryEmail;
    private String primaryMobileNumber;
    private Character gender;
    private String program;
    private String branch;

}
