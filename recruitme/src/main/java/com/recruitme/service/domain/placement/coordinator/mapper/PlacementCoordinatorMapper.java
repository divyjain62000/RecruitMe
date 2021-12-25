package com.recruitme.service.domain.placement.coordinator.mapper;

import com.recruitme.domain.PlacementCoordinator;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorResponseDTO;

public class PlacementCoordinatorMapper {

    public PlacementCoordinatorResponseDTO toResponseDTO(PlacementCoordinator placementCoordinator) {
        PlacementCoordinatorResponseDTO placementCoordinatorResponseDTO=new PlacementCoordinatorResponseDTO();
        placementCoordinatorResponseDTO.setId(placementCoordinator.getId());
        placementCoordinatorResponseDTO.setName(placementCoordinator.getName());
        placementCoordinatorResponseDTO.setPrimaryEmail(placementCoordinator.getPrimaryEmail());
        placementCoordinatorResponseDTO.setPrimaryMobileNumber(placementCoordinator.getPrimaryMobileNumber());
        placementCoordinatorResponseDTO.setGender(placementCoordinator.getGender());
        placementCoordinatorResponseDTO.setProgram(placementCoordinator.getProgram().getProgramCode());
        placementCoordinatorResponseDTO.setBranch(placementCoordinator.getBranch().getBranchCode());
        return placementCoordinatorResponseDTO;
    }

}
