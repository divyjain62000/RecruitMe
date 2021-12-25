package com.recruitme.service.domain.state;

import com.recruitme.service.domain.state.dto.StateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing State
 */
public interface StateService {

    Page<StateDTO> findAllByCountryId(Integer countryId, Pageable pageable);
}
