package com.recruitme.repository;

import com.recruitme.domain.Drive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for Drive entity
 */
@Repository
public interface DriveRepository extends JpaRepository<Drive, Integer> {
}
