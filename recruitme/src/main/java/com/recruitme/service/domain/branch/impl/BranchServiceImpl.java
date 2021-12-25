package com.recruitme.service.domain.branch.impl;

import com.recruitme.model.BranchModel;
import com.recruitme.model.ProgramModel;
import com.recruitme.service.domain.branch.BranchService;
import com.recruitme.service.domain.branch.dto.BranchDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Branch
 */
@Service
@Slf4j
public class BranchServiceImpl implements BranchService {

    @Override
    public Page<BranchDTO> findAll(Integer programId, Pageable pageable) {
        log.debug("Program Id: {}",programId);
        List<BranchDTO> branchDTOList=new LinkedList<>();
        if(programId!=null && BranchModel.programIdBranchesMap.containsKey(programId)==true)  branchDTOList=BranchModel.programIdBranchesMap.get(programId);
        else branchDTOList=BranchModel.branchList;
        log.debug("branch size: {}",branchDTOList);
        int branchDTOListSize=branchDTOList.size();
        return new PageImpl<>(branchDTOList,pageable,branchDTOListSize);
    }
}
