package com.recruitme.repository;

import com.recruitme.domain.Address;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for the {@link Address} entity.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    Optional<Address> findById(Integer id);
}
