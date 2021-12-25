package com.recruitme.repository;

import com.recruitme.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the State entity.
 */
@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
}
