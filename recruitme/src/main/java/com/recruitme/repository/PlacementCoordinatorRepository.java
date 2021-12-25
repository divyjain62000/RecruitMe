package com.recruitme.repository;

import com.recruitme.domain.PlacementCoordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for PlacementCoordinator entity
 */
@Repository
public interface PlacementCoordinatorRepository extends JpaRepository<PlacementCoordinator,Integer> {
    public Optional<PlacementCoordinator> findById(Long id);
}
