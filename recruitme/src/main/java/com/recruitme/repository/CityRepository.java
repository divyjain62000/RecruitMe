package com.recruitme.repository;

import com.recruitme.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the City entity.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
