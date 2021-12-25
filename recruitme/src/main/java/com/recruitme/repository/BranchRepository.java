package com.recruitme.repository;

import com.recruitme.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data repository for the Branch entity.
 */
@Repository
public interface BranchRepository extends JpaRepository<Branch,Integer> {
}
