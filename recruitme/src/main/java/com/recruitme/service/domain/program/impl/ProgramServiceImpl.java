package com.recruitme.service.domain.program.impl;

import com.recruitme.model.ProgramModel;
import com.recruitme.service.domain.program.ProgramService;
import com.recruitme.service.domain.program.dto.ProgramDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing Program
 */
@Service
@Slf4j
public class ProgramServiceImpl implements ProgramService {

    @Override
    public Page<ProgramDTO> findAll(Pageable pageable) {
        List<ProgramDTO> programDTOList=ProgramModel.programList;
        int programDTOListSize=programDTOList.size();
        return new PageImpl<ProgramDTO>(programDTOList,pageable,programDTOListSize);
    }
}
