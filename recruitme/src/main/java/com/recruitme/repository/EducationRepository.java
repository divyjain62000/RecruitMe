package com.recruitme.repository;

import com.recruitme.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Education entity.
 */
@Repository
public interface EducationRepository extends JpaRepository<Education,Integer> {
}
