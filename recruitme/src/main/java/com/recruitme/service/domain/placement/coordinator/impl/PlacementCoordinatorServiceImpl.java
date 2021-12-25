package com.recruitme.service.domain.placement.coordinator.impl;


import com.recruitme.domain.PlacementCoordinator;
import com.recruitme.enums.Gender;
import com.recruitme.enums.error.*;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.model.*;
import com.recruitme.repository.PlacementCoordinatorRepository;
import com.recruitme.service.domain.placement.coordinator.PlacementCoordinatorService;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorDTO;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorResponseDTO;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorSearchDTO;
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
import java.util.*;

@Service
@Slf4j
public class PlacementCoordinatorServiceImpl implements PlacementCoordinatorService {

    @Autowired
    private PlacementCoordinatorRepository placementCoordinatorRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    /**
     * Method to save PlacementCoordinator
     * @param placementCoordinatorDTO
     * @throws RecruitmeException
     */
    @Override
    public void save(PlacementCoordinatorDTO placementCoordinatorDTO) throws RecruitmeException {

        RecruitmeException recruitmeException=isValidPlacementCoordinator(placementCoordinatorDTO);

        if(recruitmeException==null) recruitmeException=new RecruitmeException();
        if(PlacementCoordinatorModel.placementCoordinatorEmailMap.containsKey(placementCoordinatorDTO.getPrimaryEmail())) {
            recruitmeException.addException(ErrorFor.PRIMARY_EMAIL_ERR.getErrorFor(), CommonError.EMAIL_ID_EXISTS.getCommonError());
        }
        if(PlacementCoordinatorModel.placementCoordinatorMobileMap.containsKey(placementCoordinatorDTO.getPrimaryMobileNumber())) {
            recruitmeException.addException(ErrorFor.PRIMARY_MOBILE_ERR.getErrorFor(), CommonError.MOBILE_NUMBER_EXISTS.getCommonError());
        }
        if(recruitmeException.getExceptions().size()>0) throw recruitmeException;
        if(placementCoordinatorDTO.getId()==null || placementCoordinatorDTO.getId()!=0) placementCoordinatorDTO.setId(Long.parseLong("0"));

        Map<String,Object> emailMap=new HashMap<>();
        emailMap.put("name",placementCoordinatorDTO.getName());
        emailMap.put("email",placementCoordinatorDTO.getPrimaryEmail());
        emailMap.put("password",placementCoordinatorDTO.getPassword());

        String password = placementCoordinatorDTO.getPassword();
        String passwordKey = EncryptionUtility.getKey();
        String encryptedPassword = EncryptionUtility.encrypt(password, passwordKey);
        placementCoordinatorDTO.setPassword(encryptedPassword);
        placementCoordinatorDTO.setPasswordKey(passwordKey);

        ModelMapper mapper=new ModelMapper();
        PlacementCoordinator placementCoordinator=mapper.map(placementCoordinatorDTO,PlacementCoordinator.class);
        placementCoordinator=this.placementCoordinatorRepository.save(placementCoordinator);
        placementCoordinatorDTO=mapper.map(placementCoordinator,PlacementCoordinatorDTO.class);

        //code to insert data in model
        PlacementCoordinatorModel.placementCoordinatorList.add(placementCoordinator);
        PlacementCoordinatorModel.placementCoordinatorIdMap.put(placementCoordinator.getId(),placementCoordinator);
        PlacementCoordinatorModel.placementCoordinatorEmailMap.put(placementCoordinator.getPrimaryEmail(),placementCoordinator);
        PlacementCoordinatorModel.placementCoordinatorMobileMap.put(placementCoordinator.getPrimaryMobileNumber(),placementCoordinator);

        //Sending welcome email
        MailDTO mailDTO=new MailDTO();
        mailDTO.setSubject("Registred Sucessfully!");
        List<String> placementCoordinatorEmails=new LinkedList<>();
        placementCoordinatorEmails.add(placementCoordinatorDTO.getPrimaryEmail());
        mailDTO.setTo(placementCoordinatorEmails);
        mailDTO.setEmailMap(emailMap);
        emailSenderService.sendWelcomeEmail(mailDTO);
    }

    /**
     * Method to edit PlacementCoordinator
     * @param placementCoordinatorDTO
     * @throws RecruitmeException
     */
    @Override
    public void edit(PlacementCoordinatorDTO placementCoordinatorDTO) throws RecruitmeException {

        RecruitmeException recruitmeException=new RecruitmeException();
        if(recruitmeException==null) recruitmeException=new RecruitmeException();
        if(placementCoordinatorDTO.getId()==null) {
            recruitmeException.addException(ErrorFor.ID_ERR.getErrorFor(), CommonError.ID_REQUIRED.getCommonError());
        }else {
            Optional<PlacementCoordinator> placementCoordinator = placementCoordinatorRepository.findById(placementCoordinatorDTO.getId());
            if (placementCoordinator.isPresent() == false)
                recruitmeException.addException(ErrorFor.ID_ERR.getErrorFor(), CommonError.INVALID_ID.getCommonError());
        }

        if(recruitmeException.getExceptions().size()>0) throw recruitmeException;


        String password = placementCoordinatorDTO.getPassword();
        String passwordKey = EncryptionUtility.getKey();
        String encryptedPassword = EncryptionUtility.encrypt(password, passwordKey);
        placementCoordinatorDTO.setPassword(encryptedPassword);
        placementCoordinatorDTO.setPasswordKey(passwordKey);

        ModelMapper mapper=new ModelMapper();
        PlacementCoordinator placementCoordinator=mapper.map(placementCoordinatorDTO,PlacementCoordinator.class);
        placementCoordinator=this.placementCoordinatorRepository.save(placementCoordinator);
        placementCoordinatorDTO=mapper.map(placementCoordinator,PlacementCoordinatorDTO.class);

        //code to insert data in model
        int size=PlacementCoordinatorModel.placementCoordinatorList.size();
        for(int i=0;i<size;i++) {
            PlacementCoordinator placementCoordinatorTmp=PlacementCoordinatorModel.placementCoordinatorList.get(i);
            if(placementCoordinatorTmp.getId()==placementCoordinator.getId()) {
                PlacementCoordinatorModel.placementCoordinatorList.set(i,placementCoordinator);
            }
        }
        PlacementCoordinatorModel.placementCoordinatorIdMap.put(placementCoordinator.getId(),placementCoordinator);
        PlacementCoordinatorModel.placementCoordinatorEmailMap.put(placementCoordinator.getPrimaryEmail(),placementCoordinator);
        PlacementCoordinatorModel.placementCoordinatorMobileMap.put(placementCoordinator.getPrimaryMobileNumber(),placementCoordinator);

    }

    /**
     * To filter PLacement Coordinator or To get all PLacement Coordinator
     * @param placementCoordinatorSearchDTO
     * @return
     */
    @Override
    public Page<PlacementCoordinatorResponseDTO> search(PlacementCoordinatorSearchDTO placementCoordinatorSearchDTO, Pageable pageable) {
        Set<PlacementCoordinatorResponseDTO> placementCoordinatorResponseDTOSet=new HashSet<>();
        ModelMapper mapper=new ModelMapper();
        log.debug("placementCoordinatorSearchDTO.getId(): {}",placementCoordinatorSearchDTO.getId());
        if(placementCoordinatorSearchDTO.getId()!=null) {
            log.debug("placementCoordinatorId not null");
            if(PlacementCoordinatorModel.placementCoordinatorIdMap.containsKey(placementCoordinatorSearchDTO.getId())) {
                log.debug("placementCoordinatorId found");
                PlacementCoordinator placementCoordinator=PlacementCoordinatorModel.placementCoordinatorIdMap.get(placementCoordinatorSearchDTO.getId());
                PlacementCoordinatorResponseDTO placementCoordinatorResponseDTO=mapper.map(placementCoordinator,PlacementCoordinatorResponseDTO.class);
                placementCoordinatorResponseDTOSet.add(placementCoordinatorResponseDTO);
            }
        }
        boolean flag=false;
        if(placementCoordinatorSearchDTO.getName()!=null || placementCoordinatorSearchDTO.getBranchId()!=null || placementCoordinatorSearchDTO.getProgramId()!=null || placementCoordinatorSearchDTO.getPrimaryEmail()!=null || placementCoordinatorSearchDTO.getPrimaryMobileNumber()!=null) {
            flag=true;
            PlacementCoordinatorModel.placementCoordinatorList.forEach((placementCoordinator)->{
                String name=placementCoordinator.getName();
                String branchId=placementCoordinator.getBranch().getId().toString();
                String programId=placementCoordinator.getProgram().getId().toString();
                String primaryMobileNumber=placementCoordinator.getPrimaryMobileNumber();
                String primaryEmail=placementCoordinator.getPrimaryEmail().toLowerCase();
                if(placementCoordinatorSearchDTO.getName()!=null && (name.toLowerCase().contains(placementCoordinatorSearchDTO.getName().toLowerCase()) || (placementCoordinatorSearchDTO.getSearch()!=null && name.toLowerCase().contains(placementCoordinatorSearchDTO.getSearch())))) {
                    PlacementCoordinatorResponseDTO placementCoordinatorResponseDTO=mapper.map(placementCoordinator,PlacementCoordinatorResponseDTO.class);
                    placementCoordinatorResponseDTOSet.add(placementCoordinatorResponseDTO);
                }
                if((placementCoordinatorSearchDTO.getBranchId()!=null && branchId!=null) && (branchId.equals(placementCoordinatorSearchDTO.getBranchId().toString()) || (placementCoordinatorSearchDTO.getSearch()!=null && branchId.equals(placementCoordinatorSearchDTO.getSearch())))){
                     PlacementCoordinatorResponseDTO placementCoordinatorResponseDTO=mapper.map(placementCoordinator,PlacementCoordinatorResponseDTO.class);
                    placementCoordinatorResponseDTOSet.add(placementCoordinatorResponseDTO);
                }
                if((placementCoordinatorSearchDTO.getProgramId()!=null && programId!=null) && (programId.equals(placementCoordinatorSearchDTO.getProgramId().toString()) || (placementCoordinatorSearchDTO.getSearch()!=null && programId.equals(placementCoordinatorSearchDTO.getSearch())))){
                    PlacementCoordinatorResponseDTO placementCoordinatorResponseDTO=mapper.map(placementCoordinator,PlacementCoordinatorResponseDTO.class);
                    placementCoordinatorResponseDTOSet.add(placementCoordinatorResponseDTO);
                }
                if(placementCoordinatorSearchDTO.getPrimaryMobileNumber()!=null && (primaryMobileNumber.contains(placementCoordinatorSearchDTO.getPrimaryMobileNumber()) || (placementCoordinatorSearchDTO.getSearch()!=null && primaryMobileNumber.contains(placementCoordinatorSearchDTO.getSearch().toLowerCase())))) {
                    PlacementCoordinatorResponseDTO placementCoordinatorResponseDTO=mapper.map(placementCoordinator,PlacementCoordinatorResponseDTO.class);
                    placementCoordinatorResponseDTOSet.add(placementCoordinatorResponseDTO);
                }
                if(placementCoordinatorSearchDTO.getPrimaryEmail()!=null && (primaryEmail.contains(placementCoordinatorSearchDTO.getPrimaryEmail().toLowerCase()) || (placementCoordinatorSearchDTO.getSearch()!=null && primaryEmail.contains(placementCoordinatorSearchDTO.getSearch().toLowerCase())))) {
                    PlacementCoordinatorResponseDTO placementCoordinatorResponseDTO=mapper.map(placementCoordinator,PlacementCoordinatorResponseDTO.class);
                    placementCoordinatorResponseDTOSet.add(placementCoordinatorResponseDTO);
                }

            });
        }

        List<PlacementCoordinatorResponseDTO> placementCoordinatorResponseDTOList=new ArrayList<>(placementCoordinatorResponseDTOSet);
        long placementCoordinatorDTOListSize=placementCoordinatorResponseDTOList.size();
        log.debug("placementCoordinatorDTOListSize: {}",placementCoordinatorDTOListSize);
        log.debug("placementCoordinatorDTOList: {}",placementCoordinatorResponseDTOList);
        if(placementCoordinatorDTOListSize==0 && flag==false) {
            PlacementCoordinatorModel.placementCoordinatorList.forEach((placementCoordinator)->{
                PlacementCoordinatorResponseDTO placementCoordinatorResponseDTO=mapper.map(placementCoordinator,PlacementCoordinatorResponseDTO.class);
                placementCoordinatorResponseDTOList.add(placementCoordinatorResponseDTO);
            });
        }

        return new PageImpl<PlacementCoordinatorResponseDTO>(placementCoordinatorResponseDTOList,pageable,placementCoordinatorDTOListSize);
    }




    private RecruitmeException isValidPlacementCoordinator(PlacementCoordinatorDTO placementCoordinatorDTO) {
        RecruitmeException recruitmeException=new RecruitmeException();
        if(recruitmeException==null) {
            recruitmeException.addException(ErrorFor.NEED_ALL_DETAIL_ERR.getErrorFor(), CommonError.NEED_ALL_DETAIL.getCommonError());
            return  recruitmeException;
        }
        if(placementCoordinatorDTO.getName()==null || placementCoordinatorDTO.getName().length()==0) {
            recruitmeException.addException(ErrorFor.NAME_ERR.getErrorFor(),CommonError.NAME_REQUIRED.getCommonError());
        }
        if(placementCoordinatorDTO.getPrimaryEmail()==null || placementCoordinatorDTO.getPrimaryEmail().length()==0) {
            recruitmeException.addException(ErrorFor.PRIMARY_EMAIL_ERR.getErrorFor(), CommonError.PRIMARY_EMAIL_REQUIRED.getCommonError());
        }
        else if(Validator.isValidEmail(placementCoordinatorDTO.getPrimaryEmail())==false) {
            recruitmeException.addException(ErrorFor.PRIMARY_EMAIL_ERR.getErrorFor(),CommonError.INVALID_EMAIL_ID.getCommonError());
        }
        if(placementCoordinatorDTO.getPrimaryMobileNumber()==null || placementCoordinatorDTO.getPrimaryMobileNumber().length()==0) {
            recruitmeException.addException(ErrorFor.PRIMARY_MOBILE_ERR.getErrorFor(), CommonError.PRIMARY_MOBILE_NUMBER_REQUIRED.getCommonError());
        }
        else if(Validator.isValidMobileNumber(placementCoordinatorDTO.getPrimaryMobileNumber())==false) {
            recruitmeException.addException(ErrorFor.PRIMARY_MOBILE_ERR.getErrorFor(), CommonError.INVALID_MOBILE_NUMBER.getCommonError());
        }
        if (placementCoordinatorDTO.getPassword() == null || placementCoordinatorDTO.getPassword().length() == 0) {
          //recruitmeException.addException(ErrorFor.PASSWORD_ERR.getErrorFor(), CommonError.PASSWORD_REQUIRED.getCommonError());
            placementCoordinatorDTO.setPassword(PasswordGenerator.generatePassword());
        }else {
            placementCoordinatorDTO.setPassword(PasswordGenerator.generatePassword());
        }
        if (placementCoordinatorDTO.getGender() == null) {
            recruitmeException.addException(ErrorFor.GENDER_ERR.getErrorFor(), CommonError.GENDER_REQUIRED.getCommonError());
        } else if (!(placementCoordinatorDTO.getGender() == Gender.MALE.getGender() || placementCoordinatorDTO.getGender() == Gender.FEMALE.getGender() || placementCoordinatorDTO.getGender() == Gender.OTHER.getGender())) {
            recruitmeException.addException(ErrorFor.GENDER_ERR.getErrorFor(), CommonError.GENDER_REQUIRED.getCommonError());
        }

        if(placementCoordinatorDTO.getProgramId()==null) {
            recruitmeException.addException(ErrorFor.PROGRAM_ERR.getErrorFor(), ProgramError.PROGRAM_NAME_REQUIRED.getProgramError());
        }
        else if(ProgramModel.programIdMap.containsKey(placementCoordinatorDTO.getProgramId())==false) {
            recruitmeException.addException(ErrorFor.PROGRAM_ERR.getErrorFor(), ProgramError.PROGRAM_NOT_EXISTS.getProgramError());
        }

        if(placementCoordinatorDTO.getBranchId()==null) {
            recruitmeException.addException(ErrorFor.BRANCH_ERR.getErrorFor(), BranchError.BRANCH_NAME_REQUIRED.getBranchError());
        }
        else if(BranchModel.branchIdMap.containsKey(placementCoordinatorDTO.getBranchId())==false || BranchModel.branchIdMap.get(placementCoordinatorDTO.getBranchId()).getProgramId()!=placementCoordinatorDTO.getProgramId()) {
                recruitmeException.addException(ErrorFor.BRANCH_ERR.getErrorFor(), BranchError.BRANCH_NOT_EXISTS.getBranchError());
        }

        if(recruitmeException.getExceptions().size()==0) return null;
        return recruitmeException;
    }


}
