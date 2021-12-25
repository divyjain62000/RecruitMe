package com.recruitme.service.domain.country;

import com.recruitme.service.domain.country.dto.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing Country
 */
public interface CountryService {

    public Page<CountryDTO> findAll(Pageable pageable);
}
