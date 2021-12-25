package com.recruitme.service.domain.placement.faculty.impl;

import com.recruitme.domain.PlacementCoordinator;
import com.recruitme.domain.PlacementPreparationFaculty;
import com.recruitme.enums.Gender;
import com.recruitme.enums.error.*;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.model.*;
import com.recruitme.repository.PlacementPreparationFacultyRepository;
import com.recruitme.service.domain.placement.faculty.dto.PlacementPreparationFacultyResponseDTO;
import com.recruitme.service.domain.placement.faculty.dto.PlacementPreparationFacultySearchDTO;
import com.recruitme.service.domain.placement.faculty.dto.PlacementPreparationFacultyDTO;
import com.recruitme.service.domain.placement.faculty.PlacementPreparationFacultyService;
import com.recruitme.service.mail.EmailSenderService;
import com.recruitme.service.mail.dto.MailDTO;
import com.recruitme.utility.encryption.EncryptionUtility;
import com.recruitme.utility.password.PasswordGenerator;
import com.recruitme.utility.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.*;

@Service
@Slf4j
public class PlacementPreparationFacultyServiceImpl implements PlacementPreparationFacultyService {

    @Autowired
    private PlacementPreparationFacultyRepository placementPreparationFacultyRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    /**
     * To add placement preparation faculty
     * @param placementPreparationFacultyDTO
     */
    @Override
    public void save(PlacementPreparationFacultyDTO placementPreparationFacultyDTO) throws RecruitmeException {
        RecruitmeException recruitmeException=null;
        recruitmeException=isValidPlacementPreparationFaculty(placementPreparationFacultyDTO);
        if(recruitmeException==null) recruitmeException=new RecruitmeException();
        if(recruitmeException.getExceptions().size()>0) throw recruitmeException;
        if(PlacementPreparationFacultyModel.placementPreparationFacultyEmailMap.containsKey(placementPreparationFacultyDTO.getPrimaryEmail())) {
            recruitmeException.addException(ErrorFor.PRIMARY_EMAIL_ERR.getErrorFor(), CommonError.EMAIL_ID_EXISTS.getCommonError());
        }
        if(PlacementPreparationFacultyModel.placementPreparationFacultyMobileMap.containsKey(placementPreparationFacultyDTO.getPrimaryMobileNumber())) {
            recruitmeException.addException(ErrorFor.PRIMARY_MOBILE_ERR.getErrorFor(), CommonError.MOBILE_NUMBER_EXISTS.getCommonError());
        }
        if(recruitmeException.getExceptions().size()>0) throw recruitmeException;

        Map<String,Object> emailMap=new HashMap<>();
        emailMap.put("name",placementPreparationFacultyDTO.getName());
        emailMap.put("email",placementPreparationFacultyDTO.getPrimaryEmail());
        emailMap.put("password",placementPreparationFacultyDTO.getPassword());

        String password = placementPreparationFacultyDTO.getPassword();
        String passwordKey = EncryptionUtility.getKey();
        String encryptedPassword = EncryptionUtility.encrypt(password, passwordKey);
        placementPreparationFacultyDTO.setPassword(encryptedPassword);
        placementPreparationFacultyDTO.setPasswordKey(passwordKey);

        ModelMapper mapper=new ModelMapper();
        PlacementPreparationFaculty placementPreparationFaculty=mapper.map(placementPreparationFacultyDTO,PlacementPreparationFaculty.class);
        placementPreparationFaculty=this.placementPreparationFacultyRepository.save(placementPreparationFaculty);
        placementPreparationFacultyDTO=mapper.map(placementPreparationFaculty,PlacementPreparationFacultyDTO.class);

        //code to insert data in model
        PlacementPreparationFacultyModel.placementPreparationFacultyList.add(placementPreparationFaculty);
        PlacementPreparationFacultyModel.placementPreparationFacultyIdMap.put(placementPreparationFaculty.getId(),placementPreparationFaculty);
        PlacementPreparationFacultyModel.placementPreparationFacultyEmailMap.put(placementPreparationFaculty.getPrimaryEmail(),placementPreparationFaculty);
        PlacementPreparationFacultyModel.placementPreparationFacultyMobileMap.put(placementPreparationFaculty.getPrimaryMobileNumber(),placementPreparationFaculty);

        //Sending welcome email
        MailDTO mailDTO=new MailDTO();
        mailDTO.setSubject("Welcome to RecruitMe");
        List<String> placementPreparationFacultyEmails=new LinkedList<>();
        placementPreparationFacultyEmails.add(placementPreparationFacultyDTO.getPrimaryEmail());
        mailDTO.setTo(placementPreparationFacultyEmails);
        mailDTO.setEmailMap(emailMap);
        emailSenderService.sendWelcomeEmail(mailDTO);

    }

    /**
     * Method to edit PlacementPreparationFaculty
     * @param placementPreparationFacultyDTO
     * @throws RecruitmeException
     */
    @Override
    public void edit(PlacementPreparationFacultyDTO placementPreparationFacultyDTO) throws RecruitmeException {

        RecruitmeException recruitmeException=new RecruitmeException();
        if(recruitmeException==null) recruitmeException=new RecruitmeException();
        if(placementPreparationFacultyDTO.getId()==null) {
            recruitmeException.addException(ErrorFor.ID_ERR.getErrorFor(), CommonError.ID_REQUIRED.getCommonError());
        }else {
            Optional<PlacementPreparationFaculty> placementPreparationFaculty = placementPreparationFacultyRepository.findById(placementPreparationFacultyDTO.getId());
            if (placementPreparationFaculty.isPresent() == false)
                recruitmeException.addException(ErrorFor.ID_ERR.getErrorFor(), CommonError.INVALID_ID.getCommonError());
        }
        if(recruitmeException.getExceptions().size()>0) throw recruitmeException;

        ModelMapper mapper=new ModelMapper();
        PlacementPreparationFaculty placementPreparationFaculty=mapper.map(placementPreparationFacultyDTO,PlacementPreparationFaculty.class);
        placementPreparationFaculty=this.placementPreparationFacultyRepository.save(placementPreparationFaculty);
        placementPreparationFacultyDTO=mapper.map(placementPreparationFaculty,PlacementPreparationFacultyDTO.class);

        //code to insert data in model
        int size=PlacementPreparationFacultyModel.placementPreparationFacultyList.size();
        for(int i=0;i<size;i++) {
            PlacementPreparationFaculty placementPreparationFacultyTmp=PlacementPreparationFacultyModel.placementPreparationFacultyList.get(i);
            if(placementPreparationFacultyTmp.getId()==placementPreparationFaculty.getId()) {
                PlacementPreparationFacultyModel.placementPreparationFacultyList.set(i,placementPreparationFaculty);
            }
        }
        PlacementPreparationFacultyModel.placementPreparationFacultyIdMap.put(placementPreparationFaculty.getId(),placementPreparationFaculty);
        PlacementPreparationFacultyModel.placementPreparationFacultyEmailMap.put(placementPreparationFaculty.getPrimaryEmail(),placementPreparationFaculty);
        PlacementPreparationFacultyModel.placementPreparationFacultyMobileMap.put(placementPreparationFaculty.getPrimaryMobileNumber(),placementPreparationFaculty);



    }



    @Override
    public Page<PlacementPreparationFacultyResponseDTO> search(PlacementPreparationFacultySearchDTO placementPreparationFacultySearchDTO, Pageable pageable) {
        Set<PlacementPreparationFacultyResponseDTO> placementPreparationFacultyResponseDTOSet=new HashSet<>();
        ModelMapper mapper=new ModelMapper();
        log.debug("placementPreparationFacultySearchDTO.getId(): {}",placementPreparationFacultySearchDTO.getId());
        if(placementPreparationFacultySearchDTO.getId()!=null) {
            log.debug("placementPreparationFacultyId not null");
            if(PlacementPreparationFacultyModel.placementPreparationFacultyIdMap.containsKey(placementPreparationFacultySearchDTO.getId())) {
                log.debug("placementPreparationFacultyId found");
                PlacementPreparationFaculty placementPreparationFaculty=PlacementPreparationFacultyModel.placementPreparationFacultyIdMap.get(placementPreparationFacultySearchDTO.getId());
                PlacementPreparationFacultyResponseDTO placementPreparationFacultyResponseDTO=mapper.map(placementPreparationFaculty,PlacementPreparationFacultyResponseDTO.class);
                placementPreparationFacultyResponseDTOSet.add(placementPreparationFacultyResponseDTO);
            }
        }
        boolean flag=false;
        if(placementPreparationFacultySearchDTO.getName()!=null || placementPreparationFacultySearchDTO.getBranchId()!=null || placementPreparationFacultySearchDTO.getProgramId()!=null || placementPreparationFacultySearchDTO.getPrimaryEmail()!=null || placementPreparationFacultySearchDTO.getPrimaryMobileNumber()!=null) {
            flag=true;
            PlacementPreparationFacultyModel.placementPreparationFacultyList.forEach((placementPreparationFaculty)->{
                String name=placementPreparationFaculty.getName();
                String branchId=placementPreparationFaculty.getBranch().getId().toString();
                String programId=placementPreparationFaculty.getProgram().getId().toString();
                String primaryMobileNumber=placementPreparationFaculty.getPrimaryMobileNumber();
                String primaryEmail=placementPreparationFaculty.getPrimaryEmail().toLowerCase();
                if(placementPreparationFacultySearchDTO.getName()!=null && (name.toLowerCase().contains(placementPreparationFacultySearchDTO.getName().toLowerCase()) || (placementPreparationFacultySearchDTO.getSearch()!=null && name.toLowerCase().contains(placementPreparationFacultySearchDTO.getSearch())))) {
                    PlacementPreparationFacultyResponseDTO placementPreparationFacultyResponseDTO=mapper.map(placementPreparationFaculty,PlacementPreparationFacultyResponseDTO.class);
                    placementPreparationFacultyResponseDTOSet.add(placementPreparationFacultyResponseDTO);
                }
                if((placementPreparationFacultySearchDTO.getBranchId()!=null && branchId!=null) && (branchId.equals(placementPreparationFacultySearchDTO.getBranchId().toString()) || (placementPreparationFacultySearchDTO.getSearch()!=null && branchId.equals(placementPreparationFacultySearchDTO.getSearch())))){
                    PlacementPreparationFacultyResponseDTO placementPreparationFacultyResponseDTO=mapper.map(placementPreparationFaculty,PlacementPreparationFacultyResponseDTO.class);
                    placementPreparationFacultyResponseDTOSet.add(placementPreparationFacultyResponseDTO);
                }
                if((placementPreparationFacultySearchDTO.getProgramId()!=null && programId!=null) && (programId.equals(placementPreparationFacultySearchDTO.getProgramId().toString()) || (placementPreparationFacultySearchDTO.getSearch()!=null && programId.equals(placementPreparationFacultySearchDTO.getSearch())))){
                    PlacementPreparationFacultyResponseDTO placementPreparationFacultyResponseDTO=mapper.map(placementPreparationFaculty,PlacementPreparationFacultyResponseDTO.class);
                    placementPreparationFacultyResponseDTOSet.add(placementPreparationFacultyResponseDTO);
                }
                if(placementPreparationFacultySearchDTO.getPrimaryMobileNumber()!=null && (primaryMobileNumber.contains(placementPreparationFacultySearchDTO.getPrimaryMobileNumber()) || (placementPreparationFacultySearchDTO.getSearch()!=null && primaryMobileNumber.contains(placementPreparationFacultySearchDTO.getSearch().toLowerCase())))) {
                    PlacementPreparationFacultyResponseDTO placementPreparationFacultyResponseDTO=mapper.map(placementPreparationFaculty,PlacementPreparationFacultyResponseDTO.class);
                    placementPreparationFacultyResponseDTOSet.add(placementPreparationFacultyResponseDTO);
                }
                if(placementPreparationFacultySearchDTO.getPrimaryEmail()!=null && (primaryEmail.contains(placementPreparationFacultySearchDTO.getPrimaryEmail().toLowerCase()) || (placementPreparationFacultySearchDTO.getSearch()!=null && primaryEmail.contains(placementPreparationFacultySearchDTO.getSearch().toLowerCase())))) {
                    PlacementPreparationFacultyResponseDTO placementPreparationFacultyResponseDTO=mapper.map(placementPreparationFaculty,PlacementPreparationFacultyResponseDTO.class);
                    placementPreparationFacultyResponseDTOSet.add(placementPreparationFacultyResponseDTO);
                }

            });
        }

        List<PlacementPreparationFacultyResponseDTO> placementPreparationFacultyResponseDTOList=new ArrayList<>(placementPreparationFacultyResponseDTOSet);
        long placementPreparationFacultyResponseDTOListSize=placementPreparationFacultyResponseDTOList.size();
        log.debug("placementPreparationFacultyResponseDTOListSize: {}",placementPreparationFacultyResponseDTOListSize);
        log.debug("placementPreparationFacultyResponseDTOList: {}",placementPreparationFacultyResponseDTOList);
        if(placementPreparationFacultyResponseDTOListSize==0 && flag==false) {
            PlacementPreparationFacultyModel.placementPreparationFacultyList.forEach((placementPreparationFaculty)->{
                PlacementPreparationFacultyResponseDTO placementPreparationFacultyResponseDTO=mapper.map(placementPreparationFaculty,PlacementPreparationFacultyResponseDTO.class);
                placementPreparationFacultyResponseDTOList.add(placementPreparationFacultyResponseDTO);
            });
        }
        return new PageImpl<PlacementPreparationFacultyResponseDTO>(placementPreparationFacultyResponseDTOList,pageable,placementPreparationFacultyResponseDTOListSize);
    }

    private RecruitmeException isValidPlacementPreparationFaculty(PlacementPreparationFacultyDTO placementPreparationFacultyDTO) {
        RecruitmeException recruitmeException=new RecruitmeException();
        if(recruitmeException==null) {
            recruitmeException.addException(ErrorFor.NEED_ALL_DETAIL_ERR.getErrorFor(), CommonError.NEED_ALL_DETAIL.getCommonError());
            return  recruitmeException;
        }
        if(placementPreparationFacultyDTO==null) {
            recruitmeException.addException(ErrorFor.NEED_ALL_DETAIL_ERR.getErrorFor(),CommonError.NEED_ALL_DETAIL.getCommonError());
        }
        if(placementPreparationFacultyDTO.getId()==null || placementPreparationFacultyDTO.getId()!=0) placementPreparationFacultyDTO.setId(Long.parseLong("0"));
        if(placementPreparationFacultyDTO.getName()==null || placementPreparationFacultyDTO.getName().length()==0) {
            recruitmeException.addException(ErrorFor.NAME_ERR.getErrorFor(),CommonError.NAME_REQUIRED.getCommonError());
        }
        if(placementPreparationFacultyDTO.getPrimaryEmail()==null || placementPreparationFacultyDTO.getPrimaryEmail().length()==0) {
            recruitmeException.addException(ErrorFor.PRIMARY_EMAIL_ERR.getErrorFor(), CommonError.PRIMARY_EMAIL_REQUIRED.getCommonError());
        }
        else if(Validator.isValidEmail(placementPreparationFacultyDTO.getPrimaryEmail())==false) {
            recruitmeException.addException(ErrorFor.PRIMARY_EMAIL_ERR.getErrorFor(),CommonError.INVALID_EMAIL_ID.getCommonError());
        }
        if(placementPreparationFacultyDTO.getPrimaryMobileNumber()==null || placementPreparationFacultyDTO.getPrimaryMobileNumber().length()==0) {
            recruitmeException.addException(ErrorFor.PRIMARY_MOBILE_ERR.getErrorFor(), CommonError.PRIMARY_MOBILE_NUMBER_REQUIRED.getCommonError());
        }
        else if(Validator.isValidMobileNumber(placementPreparationFacultyDTO.getPrimaryMobileNumber())==false) {
            recruitmeException.addException(ErrorFor.PRIMARY_MOBILE_ERR.getErrorFor(), CommonError.INVALID_MOBILE_NUMBER.getCommonError());
        }
        if (placementPreparationFacultyDTO.getPassword() == null || placementPreparationFacultyDTO.getPassword().length() == 0) {
          //  recruitmeException.addException(ErrorFor.PASSWORD_ERR.getErrorFor(), CommonError.PASSWORD_REQUIRED.getCommonError());
            placementPreparationFacultyDTO.setPassword(PasswordGenerator.generatePassword());
        }
        if (placementPreparationFacultyDTO.getGender() == null) {
            recruitmeException.addException(ErrorFor.GENDER_ERR.getErrorFor(), CommonError.GENDER_REQUIRED.getCommonError());
        } else if (!(placementPreparationFacultyDTO.getGender() == Gender.MALE.getGender() || placementPreparationFacultyDTO.getGender() == Gender.FEMALE.getGender() || placementPreparationFacultyDTO.getGender() == Gender.OTHER.getGender())) {
            recruitmeException.addException(ErrorFor.GENDER_ERR.getErrorFor(), CommonError.GENDER_REQUIRED.getCommonError());
        }

        if(placementPreparationFacultyDTO.getProgramId()==null) {
            recruitmeException.addException(ErrorFor.PROGRAM_ERR.getErrorFor(), ProgramError.PROGRAM_NAME_REQUIRED.getProgramError());
        }
        else if(ProgramModel.programIdMap.containsKey(placementPreparationFacultyDTO.getProgramId())==false) {
            recruitmeException.addException(ErrorFor.PROGRAM_ERR.getErrorFor(), ProgramError.PROGRAM_NOT_EXISTS.getProgramError());
        }
        if(placementPreparationFacultyDTO.getBranchId()==null) {
            recruitmeException.addException(ErrorFor.BRANCH_ERR.getErrorFor(), BranchError.BRANCH_NAME_REQUIRED.getBranchError());
        }
        else if(BranchModel.branchIdMap.containsKey(placementPreparationFacultyDTO.getBranchId())==false || BranchModel.branchIdMap.get(placementPreparationFacultyDTO.getBranchId()).getProgramId()!=placementPreparationFacultyDTO.getProgramId()) {
            recruitmeException.addException(ErrorFor.BRANCH_ERR.getErrorFor(), BranchError.BRANCH_NOT_EXISTS.getBranchError());
        }
        if(recruitmeException.getExceptions().size()==0) return null;
        return recruitmeException;
    }


}
