package com.recruitme.service.domain.placement.coordinator;

import com.recruitme.domain.PlacementCoordinator;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorDTO;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorResponseDTO;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

/**
 * Service interface for managing PlacementCoordinator
 */
public interface PlacementCoordinatorService {

    void save(PlacementCoordinatorDTO placementCoordinatorDTO) throws RecruitmeException;
    void edit(PlacementCoordinatorDTO placementCoordinatorDTO) throws RecruitmeException;
    Page<PlacementCoordinatorResponseDTO> search(PlacementCoordinatorSearchDTO placementCoordinatorSearchDTO, Pageable pageable);
}
