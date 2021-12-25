package com.recruitme.service.auth.impl;

import com.recruitme.domain.*;
import com.recruitme.enums.Role;
import com.recruitme.enums.error.CommonError;
import com.recruitme.enums.error.ErrorFor;
import com.recruitme.exceptions.RecruitmeException;
import com.recruitme.model.PlacementCoordinatorModel;
import com.recruitme.model.PlacementPreparationFacultyModel;
import com.recruitme.model.StudentModel;
import com.recruitme.repository.ConfirmationTokenRepository;
import com.recruitme.repository.admin.AdminRepository;
import com.recruitme.service.auth.AuthService;
import com.recruitme.service.auth.dto.UserCredentialDTO;
import com.recruitme.service.auth.dto.UserDTO;
import com.recruitme.service.domain.placement.coordinator.PlacementCoordinatorService;
import com.recruitme.service.domain.placement.coordinator.dto.PlacementCoordinatorDTO;
import com.recruitme.service.domain.placement.faculty.PlacementPreparationFacultyService;
import com.recruitme.service.domain.placement.faculty.dto.PlacementPreparationFacultyDTO;
import com.recruitme.service.domain.student.StudentService;
import com.recruitme.service.domain.student.dto.StudentDTO;
import com.recruitme.service.mail.EmailSenderService;
import com.recruitme.service.mail.dto.ConfirmationTokenDTO;
import com.recruitme.service.mail.dto.MailDTO;
import com.recruitme.utility.encryption.EncryptionUtility;
import com.recruitme.utility.token.TokenGenerationUtility;
import com.recruitme.utility.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private PlacementCoordinatorService placementCoordinatorService;

    @Autowired
    private PlacementPreparationFacultyService placementPreparationFacultyService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;



    @Override
    public UserDTO authenticateUser(UserCredentialDTO userCredentialDTO) {
        UserDTO userDTO = new UserDTO();
        if (Role.STUDENT == userCredentialDTO.getRole()) {
            log.debug("User try to login as student");
            log.debug("ucdto: {}",userCredentialDTO);
            log.debug("pemail: {}",StudentModel.studentPrimaryEmailMap.containsKey(userCredentialDTO.getEmailOrUsername()));
            if (StudentModel.studentPrimaryEmailMap.containsKey(userCredentialDTO.getEmailOrUsername())) {
                Student student = StudentModel.studentPrimaryEmailMap.get(userCredentialDTO.getEmailOrUsername());
                log.debug("Email found");
                String password = EncryptionUtility.decrypt(student.getPassword(), student.getPasswordKey());
                log.debug("Password: {}", password);
                if (password.equals(userCredentialDTO.getPassword())) {
                    userDTO.setEmail(student.getPrimaryEmail());
                    if(student.getIsValidPrimaryEmail()==false) return userDTO; //sending user dto only with email when email is not verified
                    userDTO.setId(student.getId());
                    userDTO.setEnrollmentNumber(student.getEnrollmentNumber());
                    userDTO.setRole(Role.STUDENT.getRole());
                    userDTO.setName(student.getName());
                    return userDTO;
                }
            }
        } else if (Role.ADMIN == userCredentialDTO.getRole()) {
            log.debug("User try to login as admin");
            log.debug("ucdto: {}",userCredentialDTO);
            log.debug("pemail: {}",StudentModel.studentPrimaryEmailMap.containsKey(userCredentialDTO.getEmailOrUsername()));

            Admin admin = this.adminRepository.findByUsername(userCredentialDTO.getEmailOrUsername());
            if (admin != null) {
                if (userCredentialDTO.getPassword().equals(admin.getPassword())) {
                    userDTO.setEmail(admin.getUsername());
                    userDTO.setRole(Role.ADMIN.getRole());
                    userDTO.setName("Admin");
                    return userDTO;
                }
            }
        } else if (Role.PLACEMENT_COORDINATOR == userCredentialDTO.getRole()) {
            log.debug("User try to login as placement coordinator");
            log.debug("ucdto: {}",userCredentialDTO);
            log.debug("pemail: {}",PlacementCoordinatorModel.placementCoordinatorEmailMap.containsKey(userCredentialDTO.getEmailOrUsername()));

            if (PlacementCoordinatorModel.placementCoordinatorEmailMap.containsKey(userCredentialDTO.getEmailOrUsername())) {
                PlacementCoordinator placementCoordinator = PlacementCoordinatorModel.placementCoordinatorEmailMap.get(userCredentialDTO.getEmailOrUsername());
                String password = EncryptionUtility.decrypt(placementCoordinator.getPassword(), placementCoordinator.getPasswordKey());
                log.debug("password: {}",userCredentialDTO);
                if (password.equals(userCredentialDTO.getPassword())) {
                    userDTO.setId(placementCoordinator.getId());
                    userDTO.setEmail(placementCoordinator.getPrimaryEmail());
                    userDTO.setRole(Role.PLACEMENT_COORDINATOR.getRole());
                    userDTO.setName(placementCoordinator.getName());
                    return userDTO;
                }
            }
        } else if (Role.PLACEMENT_PREPARATION_FACULTY == userCredentialDTO.getRole()) {
            log.debug("User try to login as placement preparation faculty");
            if (PlacementPreparationFacultyModel.placementPreparationFacultyEmailMap.containsKey(userCredentialDTO.getEmailOrUsername())) {
                PlacementPreparationFaculty placementPreparationFaculty = PlacementPreparationFacultyModel.placementPreparationFacultyEmailMap.get(userCredentialDTO.getEmailOrUsername());
                String password = EncryptionUtility.decrypt(placementPreparationFaculty.getPassword(), placementPreparationFaculty.getPasswordKey());
                if (password.equals(userCredentialDTO.getPassword())) {
                    userDTO.setId(placementPreparationFaculty.getId());
                    userDTO.setEmail(placementPreparationFaculty.getPrimaryEmail());
                    userDTO.setRole(Role.PLACEMENT_PREPARATION_FACULTY.getRole());
                    userDTO.setName(placementPreparationFaculty.getName());
                    return userDTO;
                }
            }
        }
        return userDTO;
    }

    public boolean resetPassword(String token,String password) throws RecruitmeException {
        ConfirmationToken confirmationToken = this.confirmationTokenRepository.findByToken(token);
        if (confirmationToken == null) {
            return false;
        }
        String passwordKey = EncryptionUtility.getKey();
        String encryptedPassword = EncryptionUtility.encrypt(password, passwordKey);
        if (confirmationToken.getStudent() != null) {
            Student student = confirmationToken.getStudent();
            student.setPassword(encryptedPassword);
            student.setPasswordKey(passwordKey);
            ModelMapper mapper = new ModelMapper();
            StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
            this.studentService.edit(studentDTO);
            this.confirmationTokenRepository.delete(confirmationToken);
            return true;
        }
        else if (confirmationToken.getPlacementCoordinator() != null) {
            PlacementCoordinator placementCoordinator = confirmationToken.getPlacementCoordinator();
            placementCoordinator.setPassword(password);
            placementCoordinator.setPasswordKey(passwordKey);
            ModelMapper mapper = new ModelMapper();
            PlacementCoordinatorDTO placementCoordinatorDTO = mapper.map(placementCoordinator, PlacementCoordinatorDTO.class);
            this.placementCoordinatorService.edit(placementCoordinatorDTO);
            this.confirmationTokenRepository.delete(confirmationToken);
            return true;
        }
        else if (confirmationToken.getPlacementPreparationFaculty() != null) {
            PlacementPreparationFaculty placementPreparationFaculty = confirmationToken.getPlacementPreparationFaculty();
            placementPreparationFaculty.setPassword(encryptedPassword);
            placementPreparationFaculty.setPasswordKey(passwordKey);
            ModelMapper mapper = new ModelMapper();
            PlacementPreparationFacultyDTO placementPreparationFacultyDTO = mapper.map(placementPreparationFaculty, PlacementPreparationFacultyDTO.class);
            this.placementPreparationFacultyService.edit(placementPreparationFacultyDTO);
            this.confirmationTokenRepository.delete(confirmationToken);
            return true;
        }
        return false;
    }

    private boolean isEmailExists(UserCredentialDTO userCredentialDTO) {
        if (userCredentialDTO.getRole() == Role.STUDENT) {
            if (StudentModel.studentPrimaryEmailMap.containsKey(userCredentialDTO.getEmailOrUsername()) == false) {
                return false;
            }
        } else if (userCredentialDTO.getRole() == Role.PLACEMENT_COORDINATOR) {
            if (PlacementCoordinatorModel.placementCoordinatorEmailMap.containsKey(userCredentialDTO.getEmailOrUsername()) == false) {
                return false;
            }
        } else if (userCredentialDTO.getRole() == Role.PLACEMENT_PREPARATION_FACULTY) {
            if (PlacementPreparationFacultyModel.placementPreparationFacultyEmailMap.containsKey(userCredentialDTO.getEmailOrUsername()) == false) {
                return false;
            }
        }
        return true;
    }



    @Override
    public boolean verifyEmail(String token) throws RecruitmeException {
        ConfirmationToken confirmationToken = this.confirmationTokenRepository.findByToken(token);
        if (confirmationToken == null) {
            log.debug("NULL");
            return false;
        }
        if (confirmationToken.getStudent() != null) {
            Student student = confirmationToken.getStudent();
            student.setIsValidPrimaryEmail(true);
            ModelMapper mapper = new ModelMapper();
            StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
            log.debug("Student ID: {}",studentDTO.getId());
            log.debug("StudentDTO: {}",studentDTO);
            this.studentService.edit(studentDTO);
            this.confirmationTokenRepository.delete(confirmationToken);
            return true;
        }
        return false;
    }

    @Override
    public void requestToRestPassaword(UserCredentialDTO userCredentialDTO) throws RecruitmeException {
        RecruitmeException recruitmeException=new RecruitmeException();
        if(userCredentialDTO==null) {
            recruitmeException.addException(ErrorFor.NEED_ALL_DETAIL_ERR.getErrorFor(), CommonError.NEED_ALL_DETAIL.getCommonError());
            throw recruitmeException;
        }
        if(userCredentialDTO.getEmailOrUsername()==null || userCredentialDTO.getEmailOrUsername().length()==0) {
            recruitmeException.addException(ErrorFor.EMAIL_ERR.getErrorFor(), CommonError.PRIMARY_EMAIL_REQUIRED.getCommonError());
        }else if(Validator.isValidEmail(userCredentialDTO.getEmailOrUsername())==false) {
            recruitmeException.addException(ErrorFor.EMAIL_ERR.getErrorFor(), CommonError.INVALID_EMAIL_ID.getCommonError());
        }
        if(userCredentialDTO.getRole()==null) {
            recruitmeException.addException(ErrorFor.ROLE_ERR.getErrorFor(), CommonError.ROLE_REQUIRED.getCommonError());
        }
        if(recruitmeException!=null && recruitmeException.getExceptions().size()>0) throw recruitmeException;
        ModelMapper mapper=new ModelMapper();
        if(userCredentialDTO.getRole()==Role.STUDENT) {
            if(StudentModel.studentPrimaryEmailMap.containsKey(userCredentialDTO.getEmailOrUsername())==false) {
                recruitmeException.addException(ErrorFor.EMAIL_ERR.getErrorFor(), CommonError.INVALID_EMAIL_ID.getCommonError());
                throw  recruitmeException;
            }
            Student student=StudentModel.studentPrimaryEmailMap.get(userCredentialDTO.getEmailOrUsername());
            StudentDTO studentDTO=mapper.map(student,StudentDTO.class);
            ConfirmationTokenDTO confirmationTokenDTO = TokenGenerationUtility.createTokenForStudent(studentDTO);
            ConfirmationToken confirmationToken = mapper.map(confirmationTokenDTO, ConfirmationToken.class);
            confirmationToken = this.confirmationTokenRepository.save(confirmationToken);
            MailDTO mailDTO = new MailDTO();
            mailDTO.setSubject("Reset Password");
            Map<String,Object> emailMap=new HashMap<>();
            emailMap.put("name",studentDTO.getName());
            emailMap.put("token",confirmationToken.getToken());
            mailDTO.setEmailMap(emailMap);
            List<String> studentEmails = new ArrayList<>();
            studentEmails.add(studentDTO.getPrimaryEmail());
            mailDTO.setTo(studentEmails);
            this.emailSenderService.sendResetPasswordEmail(mailDTO, confirmationTokenDTO);
        }
        else if(userCredentialDTO.getRole()==Role.PLACEMENT_COORDINATOR) {
            if(PlacementCoordinatorModel.placementCoordinatorEmailMap.containsKey(userCredentialDTO.getEmailOrUsername())==false) {
                recruitmeException.addException(ErrorFor.EMAIL_ERR.getErrorFor(), CommonError.INVALID_EMAIL_ID.getCommonError());
                throw  recruitmeException;
            }
            PlacementCoordinator placementCoordinator=PlacementCoordinatorModel.placementCoordinatorEmailMap.get(userCredentialDTO.getEmailOrUsername());
            PlacementCoordinatorDTO placementCoordinatorDTO=mapper.map(placementCoordinator,PlacementCoordinatorDTO.class);
            ConfirmationTokenDTO confirmationTokenDTO = TokenGenerationUtility.createTokenPlacementCoordinator(placementCoordinatorDTO);
            ConfirmationToken confirmationToken = mapper.map(confirmationTokenDTO, ConfirmationToken.class);
            confirmationToken = this.confirmationTokenRepository.save(confirmationToken);
            MailDTO mailDTO = new MailDTO();
            mailDTO.setSubject("Reset Password");
            Map<String,Object> emailMap=new HashMap<>();
            emailMap.put("name",placementCoordinatorDTO.getName());
            emailMap.put("token",confirmationToken.getToken());
            mailDTO.setEmailMap(emailMap);
            List<String> studentEmails = new ArrayList<>();
            studentEmails.add(placementCoordinatorDTO.getPrimaryEmail());
            mailDTO.setTo(studentEmails);
            this.emailSenderService.sendResetPasswordEmail(mailDTO, confirmationTokenDTO);
        }
        else if(userCredentialDTO.getRole()==Role.PLACEMENT_PREPARATION_FACULTY) {
            if(PlacementPreparationFacultyModel.placementPreparationFacultyEmailMap.containsKey(userCredentialDTO.getEmailOrUsername())==false) {
                recruitmeException.addException(ErrorFor.EMAIL_ERR.getErrorFor(), CommonError.INVALID_EMAIL_ID.getCommonError());
                throw  recruitmeException;
            }
            PlacementPreparationFaculty placementPreparationFaculty=PlacementPreparationFacultyModel.placementPreparationFacultyEmailMap.get(userCredentialDTO.getEmailOrUsername());
            PlacementPreparationFacultyDTO placementPreparationFacultyDTO=mapper.map(placementPreparationFaculty,PlacementPreparationFacultyDTO.class);
            ConfirmationTokenDTO confirmationTokenDTO = TokenGenerationUtility.createTokenPlacementPreparationFaculty(placementPreparationFacultyDTO);
            ConfirmationToken confirmationToken = mapper.map(confirmationTokenDTO, ConfirmationToken.class);
            confirmationToken = this.confirmationTokenRepository.save(confirmationToken);
            MailDTO mailDTO = new MailDTO();
            mailDTO.setSubject("Reset Password");
            Map<String,Object> emailMap=new HashMap<>();
            emailMap.put("name",placementPreparationFacultyDTO.getName());
            emailMap.put("token",confirmationToken.getToken());
            mailDTO.setEmailMap(emailMap);
            List<String> studentEmails = new ArrayList<>();
            studentEmails.add(placementPreparationFacultyDTO.getPrimaryEmail());
            mailDTO.setTo(studentEmails);
            this.emailSenderService.sendResetPasswordEmail(mailDTO, confirmationTokenDTO);
        }

    }


}
