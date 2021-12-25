package com.recruitme.service.domain.placement.faculty;

import com.recruitme.domain.PlacementPreparationFaculty;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorDTO;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorSearchDTO;
import com.recruitme.service.domain.placement.faculty.dto.PlacementPreparationFacultyDTO;
import com.recruitme.service.domain.placement.faculty.dto.PlacementPreparationFacultyResponseDTO;
import com.recruitme.service.domain.placement.faculty.dto.PlacementPreparationFacultySearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlacementPreparationFacultyService {

    void save(PlacementPreparationFacultyDTO placementPreparationFacultyDTO) throws RecruitmeException;
    void edit(PlacementPreparationFacultyDTO placementPreparationFacultyDTO) throws RecruitmeException;
    Page<PlacementPreparationFacultyResponseDTO> search(PlacementPreparationFacultySearchDTO placementPreparationFacultySearchDTODTO, Pageable pageable);

}
