package com.recruitme.service.domain.city;

import com.recruitme.service.domain.city.dto.CityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing City
 */
public interface CityService {

    Page<CityDTO> findAllByStateId(Integer stateId, Pageable pageable);
}
