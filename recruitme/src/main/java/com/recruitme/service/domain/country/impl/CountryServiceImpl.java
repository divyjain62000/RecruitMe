package com.recruitme.service.domain.country.impl;

import com.recruitme.model.CountryModel;
import com.recruitme.service.domain.country.CountryService;
import com.recruitme.service.domain.country.dto.CountryDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Country
 */
@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    /**
     * To get all conutry
     * @return the {@link List<CountryDTO>}
     */
    @Override
    public Page<CountryDTO> findAll(Pageable pageable) {
        List<CountryDTO> countryDTOList=CountryModel.countryList;
        int coutryDTOListSize=countryDTOList.size();
        return new PageImpl<>(countryDTOList,pageable,coutryDTOListSize);
    }
}
