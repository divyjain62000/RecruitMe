package com.recruitme.repository;

import com.recruitme.domain.PlacementPreparationFaculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for PlacementPreparationFaculty entity
 */
@Repository
public interface PlacementPreparationFacultyRepository extends JpaRepository<PlacementPreparationFaculty,Long> {

}
