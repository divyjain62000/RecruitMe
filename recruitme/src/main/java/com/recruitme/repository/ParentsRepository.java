package com.recruitme.repository;

import com.recruitme.domain.Parents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Parents entity.
 */
@Repository
public interface ParentsRepository extends JpaRepository<Parents, Integer> {
}
