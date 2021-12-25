package com.recruitme.repository;

import com.recruitme.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Country entity.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {

}
