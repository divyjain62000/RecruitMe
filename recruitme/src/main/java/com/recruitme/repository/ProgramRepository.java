package com.recruitme.repository;

import com.recruitme.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Program entity.
 */
@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {
}
