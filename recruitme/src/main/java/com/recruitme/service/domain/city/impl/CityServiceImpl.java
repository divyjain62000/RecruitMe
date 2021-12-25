
package com.recruitme.service.domain.city.impl;

import com.recruitme.model.CityModel;
import com.recruitme.repository.CityRepository;
import com.recruitme.service.domain.program.dto.ProgramDTO;
import com.recruitme.service.domain.city.CityService;
import com.recruitme.service.domain.city.dto.CityDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing City
 */
@Service
@Slf4j
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Page<CityDTO> findAllByStateId(Integer stateId, Pageable pageable) {
        ModelMapper modelMapper=new ModelMapper();
        List<CityDTO> cityDTOList=CityModel.cityStateIdMap.get(stateId);
        int cityDTOListSize=cityDTOList.size();
        return new PageImpl<>(cityDTOList,pageable,cityDTOListSize);
    }
}

