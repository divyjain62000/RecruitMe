package com.recruitme.service.domain.state.impl;

import com.recruitme.model.StateModel;
import com.recruitme.service.domain.program.dto.ProgramDTO;
import com.recruitme.service.domain.state.StateService;
import com.recruitme.service.domain.state.dto.StateDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing State
 */
@Service
@Slf4j
public class StateServiceImpl implements StateService {

    /**
     * To get all state of a country
     * @param countryId
     * @return
     */
    @Override
    public Page<StateDTO> findAllByCountryId(Integer countryId,Pageable pageable) {
        ModelMapper modelMapper=new ModelMapper();
        List<StateDTO> stateDTOList=StateModel.stateCountryIdMap.get(countryId);
        System.out.println("Program DTO List: {}"+stateDTOList.size());
        log.debug("log test");
        int stateDTOListSize=stateDTOList.size();
        return new PageImpl<StateDTO>(stateDTOList,pageable,stateDTOListSize);
    }
}

