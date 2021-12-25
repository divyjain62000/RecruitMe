package com.recruitme.service.domain.parents.impl;

import com.recruitme.domain.Parents;
import com.recruitme.enums.error.CommonError;
import com.recruitme.enums.error.ErrorFor;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.repository.ParentsRepository;
import com.recruitme.service.domain.parents.ParentsService;
import com.recruitme.service.domain.parents.dto.ParentsDTO;
import com.recruitme.utility.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for managing Parents
 */
@Service
@Slf4j
public class ParentsServiceImpl implements ParentsService {

    @Autowired
    ParentsRepository parentsRepository;

    /**
     *  method to add parents
     * @param parentsDTO
     * @throws RecruitmeException
     */
    @Override
    public Integer save(ParentsDTO parentsDTO) throws RecruitmeException
    {
        RecruitmeException recruitmeException=new RecruitmeException();
        if(parentsDTO.getId()==null || parentsDTO.getId()!=0) parentsDTO.setId(0);
        if(parentsDTO.getMobileNumber()==null || parentsDTO.getMobileNumber().length()==0) recruitmeException.addException(ErrorFor.MOBILE_NUMBER_ERR.getErrorFor(),CommonError.MOBILE_NUMBER_REQUIRED.getCommonError());
        else if(Validator.isValidMobileNumber(parentsDTO.getMobileNumber())==false) {
            recruitmeException.addException(ErrorFor.MOBILE_NUMBER_ERR.getErrorFor(),CommonError.INVALID_MOBILE_NUMBER.getCommonError());
        }
        if(parentsDTO.getFatherName()==null || parentsDTO.getFatherName().length()==0) {
            recruitmeException.addException(ErrorFor.FATHER_NAME_ERR.getErrorFor(),CommonError.FATHER_NAME_REQUIRED.getCommonError());
        }
        if(parentsDTO.getMotherName()==null || parentsDTO.getMotherName().length()==0) {
            recruitmeException.addException(ErrorFor.MOTHER_NAME_ERR.getErrorFor(),CommonError.MOTHER_NAME_REQUIRED.getCommonError());
        }
        if(recruitmeException!=null && recruitmeException.getExceptions().size()>0) throw  recruitmeException;
        ModelMapper modelMapper=new ModelMapper();
        Parents parents=modelMapper.map(parentsDTO, Parents.class);
        parents=parentsRepository.save(parents);
        return parents.getId();
    }

    /**
     *  method to edit parents
     * @param parentsDTO
     * @throws RecruitmeException
     */
    @Override
    public void edit(ParentsDTO parentsDTO) throws RecruitmeException {
        RecruitmeException recruitmeException=new RecruitmeException();

        if(parentsDTO.getMobileNumber()==null || parentsDTO.getMobileNumber().length()==0) recruitmeException.addException(ErrorFor.MOBILE_NUMBER_ERR.getErrorFor(),CommonError.MOBILE_NUMBER_REQUIRED.getCommonError());
        else if(Validator.isValidMobileNumber(parentsDTO.getMobileNumber())==false) {
            recruitmeException.addException(ErrorFor.MOBILE_NUMBER_ERR.getErrorFor(),CommonError.INVALID_MOBILE_NUMBER.getCommonError());
        }
        if(parentsDTO.getFatherName()==null || parentsDTO.getFatherName().length()==0) {
            recruitmeException.addException(ErrorFor.FATHER_NAME_ERR.getErrorFor(),CommonError.FATHER_NAME_REQUIRED.getCommonError());
        }
        if(parentsDTO.getMotherName()==null || parentsDTO.getMotherName().length()==0) {
            recruitmeException.addException(ErrorFor.MOTHER_NAME_ERR.getErrorFor(),CommonError.MOTHER_NAME_REQUIRED.getCommonError());
        }

        if(parentsDTO.getId()==null) {
            recruitmeException.addException(ErrorFor.ID_ERR.getErrorFor(), CommonError.ID_REQUIRED.getCommonError());
        }else {
            Optional<Parents> parent = parentsRepository.findById(parentsDTO.getId());
            log.debug("parent.isPresent(): {}",parent.isPresent());
            if (parent.isPresent() == false)
                recruitmeException.addException(ErrorFor.ID_ERR.getErrorFor(), CommonError.INVALID_ID.getCommonError());
        }
        if(recruitmeException!=null && recruitmeException.getExceptions().size()>0) throw  recruitmeException;


        if(recruitmeException.getExceptions().size()>0) throw recruitmeException;
        ModelMapper modelMapper=new ModelMapper();
        log.debug("parentsDTO: {}",parentsDTO);
        Parents parents=modelMapper.map(parentsDTO, Parents.class);
        log.debug("parents: {}",parents);
         parentsRepository.save(parents);
    }

    @Override
    public ParentsDTO findById(Integer id) {
        if(id==null) return null;
        Optional<Parents> parents=this.parentsRepository.findById(id);
        if(parents.isPresent()) {
            ModelMapper mapper=new ModelMapper();
            ParentsDTO parentsDTO=mapper.map(parents,ParentsDTO.class);
            return parentsDTO;
        }
        return null;
    }

    @Override
    public void delete(Parents parents) {
        this.parentsRepository.delete(parents);
    }

}
